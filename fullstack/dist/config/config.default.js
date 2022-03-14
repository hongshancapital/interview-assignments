"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = {
    // use for cookie sign key, should change to your own and keep security
    keys: 'urlserver',
    koa: {
        port: 7001,
    },
    sequelize: {
        options: [
            {
                database: 'url_d1',
                username: 'test',
                password: '123456',
                host: '127.0.0.1',
                port: 3306,
                encrypt: false,
                timezone: '+08:00',
            },
            {
                database: 'url_d2',
                username: 'test',
                password: '123456',
                host: '127.0.0.1',
                port: 3306,
                encrypt: false,
                timezone: '+08:00',
            },
        ],
        sync: false,
    },
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiY29uZmlnLmRlZmF1bHQuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyIuLi8uLi9zcmMvY29uZmlnL2NvbmZpZy5kZWZhdWx0LnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7O0FBRUEsa0JBQWU7SUFDYix1RUFBdUU7SUFDdkUsSUFBSSxFQUFFLFdBQVc7SUFDakIsR0FBRyxFQUFFO1FBQ0gsSUFBSSxFQUFFLElBQUk7S0FDWDtJQUNELFNBQVMsRUFBRTtRQUNULE9BQU8sRUFBRTtZQUNQO2dCQUNFLFFBQVEsRUFBRSxRQUFRO2dCQUNsQixRQUFRLEVBQUUsTUFBTTtnQkFDaEIsUUFBUSxFQUFFLFFBQVE7Z0JBQ2xCLElBQUksRUFBRSxXQUFXO2dCQUNqQixJQUFJLEVBQUUsSUFBSTtnQkFDVixPQUFPLEVBQUUsS0FBSztnQkFDZCxRQUFRLEVBQUUsUUFBUTthQUNuQjtZQUNEO2dCQUNFLFFBQVEsRUFBRSxRQUFRO2dCQUNsQixRQUFRLEVBQUUsTUFBTTtnQkFDaEIsUUFBUSxFQUFFLFFBQVE7Z0JBQ2xCLElBQUksRUFBRSxXQUFXO2dCQUNqQixJQUFJLEVBQUUsSUFBSTtnQkFDVixPQUFPLEVBQUUsS0FBSztnQkFDZCxRQUFRLEVBQUUsUUFBUTthQUNuQjtTQUNGO1FBQ0QsSUFBSSxFQUFFLEtBQUs7S0FDWjtDQUNjLENBQUMifQ==