const baseUrl = {
  URL_CORS: {
    development: {
      shorturl: [
        'http://localhost:3006',
        'http://localhost:3332',
        
        'http://api.url.co',
        'http://admin.url.co',
      ],
    },
    production: {
      shorturl: [
        'https://api.url.co',
        'https://admin.url.co'
      ],
    }
  },
  api: {
    port: {
      shorturl: '3332',
    },
    development: {
      shorturl: 'http://api.url.co/api',
    },
    production: {
      shorturl: 'https://api.url.co/api',
    }
  },
  editor: {
    development: {
      shorturl: '//editor.url.co',
    },
    production: {
      shorturl: '//editor.shorturl.co',
    }
  },
  admin: {
    development: {
      shorturl: '//admin.url.co',
    },
    production: {
      shorturl: '//admin.shorturl.co',
    }
  },
  domain: {
    development: {
      shorturl: 'url.co',
    },
    production: {
      shorturl: 'url.co',
    }
  }
}

const NODE_ENV = process.env.NODE_ENV || 'production';
const SERVER_ENV = process.env.SERVER_ENV || 'shorturl';

const URLS = {
  NODE_ENV: NODE_ENV,
  SERVER_ENV: SERVER_ENV,
  URL_DOMAIN: baseUrl['domain'][NODE_ENV][SERVER_ENV],
  URL_API: baseUrl['api'][NODE_ENV][SERVER_ENV],
  URL_ADMIN: baseUrl['admin'][NODE_ENV][SERVER_ENV],
  URL_API_PORT: baseUrl['api']['port'][SERVER_ENV],
  URL_CORS: baseUrl['URL_CORS'][NODE_ENV][SERVER_ENV],
}

module.exports = URLS
