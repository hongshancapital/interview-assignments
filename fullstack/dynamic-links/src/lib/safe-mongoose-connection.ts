import mongoose, { ConnectOptions } from 'mongoose';

/** Callback for establishing or re-stablishing mongo connection */
interface IOnConnectedCallback {
  (mongoUrl: string): void;
}

interface SafeMongooseConnectionOptions {
  mongoUrl: string;
  mongooseConnectionOptions?: ConnectOptions;
  retryDelayMs?: number
  debugCallback?: (collectionName: string, method: string, query: any, doc: string) => void;
  onStartConnection?: (mongoUrl: string) => void;
  onConnectionError?: (error: Error, mongoUrl: string) => void;
  onConnectionRetry?: (mongoUrl: string) => void;
}

const defaultMongooseConnectionOptions: ConnectOptions = {
  autoCreate: true,
  autoIndex: true
};

/**
 * A Mongoose Connection wrapper class to
 * help with mongo connection issues.
 *
 * This library tries to auto-reconnect to
 * MongoDB without crashing the server.
 * @author Sidhant Panda
 */
export default class SafeMongooseConnection {
  /** Safe Mongoose Connection options */
  private readonly options: SafeMongooseConnectionOptions;

  /** Callback when mongo connection is established or re-established */
  private onConnectedCallback?: IOnConnectedCallback;

  /**
   * Internal flag to check if connection established for
   * first time or after a disconnection
   */
  private isConnectedBefore: boolean = false;

  private shouldCloseConnection: boolean = false;

  /** Delay between retrying connecting to Mongo */
  private retryDelayMs: number = 2000;

  /** Mongo connection options to be passed Mongoose */
  private readonly mongoConnectionOptions: ConnectOptions = defaultMongooseConnectionOptions;

  private connectionTimeout?: NodeJS.Timeout;

  /**
   * Start mongo connection
   * @param mongoUrl MongoDB URL
   * @param onConnectedCallback callback to be called when mongo connection is successful
   */
  constructor(options: SafeMongooseConnectionOptions) {
    this.options = options;
    mongoose.connection.on('error', this.onError);
    mongoose.connection.on('connected', this.onConnected);
    mongoose.connection.on('disconnected', this.onDisconnected);
    mongoose.connection.on('reconnected', this.onReconnected);

    if (options.debugCallback) {
      mongoose.set('debug', options.debugCallback);
    }
    if (options.retryDelayMs) {
      this.retryDelayMs = options.retryDelayMs;
    }
  }

  /** Close mongo connection */
  public close(onClosed: (err: any) => void = () => { }, force: boolean = false) {
    if (this.connectionTimeout) {
      clearTimeout(this.connectionTimeout);
    }
    this.shouldCloseConnection = true;
    mongoose.connection.close(force, onClosed);
  }

  /** Start mongo connection */
  public connect(onConnectedCallback: IOnConnectedCallback) {
    this.onConnectedCallback = onConnectedCallback;
    this.startConnection();
  }

  private startConnection = () => {
    if (this.options.onStartConnection) {
      this.options.onStartConnection(this.options.mongoUrl);
    }
    mongoose.connect(this.options.mongoUrl, this.mongoConnectionOptions).catch(() => { });
  }

  /**
   * Handler called when mongo connection is established
   */
  private onConnected = () => {
    this.isConnectedBefore = true;
    this.onConnectedCallback?.(this.options.mongoUrl);
  };

  /** Handler called when mongo gets re-connected to the database */
  private onReconnected = () => {
    this.onConnectedCallback?.(this.options.mongoUrl);
  };

  /** Handler called for mongo connection errors */
  private onError = () => {
    if (this.options.onConnectionError) {
      const error = new Error(`Could not connect to MongoDB at ${this.options.mongoUrl}`);
      this.options.onConnectionError(error, this.options.mongoUrl);
    }
  };

  /** Handler called when mongo connection is lost */
  private onDisconnected = () => {
    if (!this.isConnectedBefore && !this.shouldCloseConnection) {
      this.connectionTimeout = setTimeout(() => {
        this.startConnection();
        this.connectionTimeout && clearTimeout(this.connectionTimeout);
      }, this.retryDelayMs);
      if (this.options.onConnectionRetry) {
        this.options.onConnectionRetry(this.options.mongoUrl);
      }
    }
  };
}
