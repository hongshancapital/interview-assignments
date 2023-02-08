import { Types, SchemaDefinition, FilterQuery, UpdateQuery, QueryOptions, UpdateWithAggregationPipeline, SchemaDefinitionType, Model, AnyKeys, IndexOptions, SortOrder } from 'mongoose';
import mongodb from '@/tools/mongodb';

export default class MongoBase<CM>{
    protected collectionName: string;
    private schemaModel: SchemaDefinition<SchemaDefinitionType<CM>>;
    private index: { [key: string]: IndexOptions } | undefined;

    /**
     * Creates an instance of MongoBase.
     * @param {string} collectionName mongodb的集合(表)名称
     * @param {SchemaDefinition<SchemaDefinitionType<CM>>} model mongodb的集合(表)结构
     * @param {{ [key: string]: IndexOptions }} [_index] mongodb的集合(表)索引
     * @memberof MongoBase
     */
    constructor(collectionName: string, model: SchemaDefinition<SchemaDefinitionType<CM>>, _index?: { [key: string]: IndexOptions }) {
        this.collectionName = collectionName;
        this.schemaModel = model;
        this.index = _index;
    }

    private get model(): Model<CM> {
        const _schema = new mongodb.schema(this.schemaModel, { _id: false, versionKey: false, timestamps: { createdAt: true, updatedAt: true } });

        if (this.index) {
            for (const key in this.index) {
                _schema.index({ [key]: 1 }, this.index[key]);
            }
        }

        return mongodb.server.model<CM>(this.collectionName, _schema, this.collectionName);
    }

    private id(data: AnyKeys<CM> | Array<AnyKeys<CM>>): Array<AnyKeys<CM & { _id: string }>> {
        const result: Array<AnyKeys<CM & { _id: string }>> = [];

        if (Array.isArray(data)) {
            for (let i = 0; i < data.length; i++) {
                // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                // @ts-ignore
                if (typeof data[i]._id !== 'string' || typeof data[i]._id === 'string' && data[i]._id?.trim() === '') {
                    result.push({
                        ...data[i],
                        _id: new Types.ObjectId().toString()
                    });
                }
            }
        } else {
            result.push({
                ...data,
                // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                // @ts-ignore
                _id: data._id || new Types.ObjectId().toString()
            });
        }

        return result;
    }

    async insertOne(data: CM): Promise<CM> {
        return await new this.model(this.id(data)[0]).save() as unknown as CM;
    }

    async insertMany(data: Array<CM>): Promise<Array<CM>> {
        return await this.model.insertMany(data.map(a => this.id(a)), { lean: true }) as unknown as Array<CM>;
    }

    async removeOne(query: FilterQuery<CM>): Promise<{ deletedCount: number }> {
        return await this.model.deleteOne(query);
    }

    async removeMany(query: FilterQuery<CM>): Promise<{ deletedCount: number }> {
        return await this.model.deleteMany(query);
    }

    async updateOne(query: FilterQuery<CM>, update: UpdateQuery<CM> | UpdateWithAggregationPipeline, options?: QueryOptions<CM>): Promise<{
        acknowledged: boolean
        modifiedCount: number
        upsertedId: null | string
        upsertedCount: number
        matchedCount: number
    }> {
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        return await this.model.updateOne(query, update, options);
    }

    async upsertOne(query: FilterQuery<CM>, update: UpdateQuery<CM> | UpdateWithAggregationPipeline, options?: QueryOptions<CM>) {
        return await this.updateOne(query, update, { ...options, upsert: true });
    }

    async updateMany(query: FilterQuery<CM>, update: UpdateQuery<CM> | UpdateWithAggregationPipeline, options?: QueryOptions<CM>): Promise<{
        acknowledged: boolean
        modifiedCount: number
        upsertedId: null | string
        upsertedCount: number
        matchedCount: number
    }> {

        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        return await this.model.updateMany(query, update, options);
    }

    /**upsert尽量不要触发insert，否则会生成一个ObjectId构建的_id，除非指定_id，并且collection里面的default默认设置的字段也不会有 */
    async upsertMany(query: FilterQuery<CM>, update: UpdateQuery<CM> | UpdateWithAggregationPipeline, options?: QueryOptions<CM>) {
        return await this.updateMany(query, update, { ...options, upsert: true });
    }

    async find(query?: FilterQuery<CM>, options?: QueryOptions<CM>) {
        return await this.model.find(query || {}, null, options).lean();
    }

    async findOne(query: FilterQuery<CM>, options?: QueryOptions<CM>): Promise<CM | null> {
        return await this.model.findOne(query, null, options).lean();
    }

    async findById(_id: string, options?: QueryOptions<CM>): Promise<CM | null> {
        return await this.model.findById(_id, null, options).lean();
    }

    async paging<K extends keyof CM>(query: FilterQuery<CM>, limit: number, skip: number, sort?: Record<K, 'asc' | 'desc' | 'ascending' | 'descending'>, options?: QueryOptions<CM>): Promise<{
        list: Array<CM>,
        total: number
    }> {
        return {
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            list: await this.model.find(query, null, options).sort((() => {
                const obj: { [key: string]: SortOrder } = {};

                if (sort) {
                    Object.keys(sort).filter(a => !!sort[a]).map(b => obj[b] = sort[b]);
                }
                return obj;
            })()).skip(skip || 0).limit(limit).lean(),
            total: await this.count(query)
        };
    }

    async count(query?: FilterQuery<CM>): Promise<number> {
        if (Object.keys(query || {}).length > 0) {
            return await this.model.countDocuments(query || {});
        } else {
            return await this.model.estimatedDocumentCount();
        }
    }

    // // eslint-disable-next-line @typescript-eslint/no-explicit-any
    // async aggregate(aggregations: Array<any>): Promise<Aggregate<Array<any>>> {
    //     return await this.model.aggregate(aggregations);
    // }
}
