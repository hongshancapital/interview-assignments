import { SchemaDefinition } from 'mongoose';

import BaseDb from './base';

export default new class Domain extends BaseDb<DomainModel> {
    /**
     * Creates an instance of Domain.
     * @memberof Domain
     */
    constructor() {
        const _model: SchemaDefinition<DomainModel> =
        {
            _id: { type: String, required: true, trim: true },
            name: { type: String, trim: true },
            url: { type: String, required: true, trim: true },
            compressed: { type: String, required: true, trim: true },
            type: { type: String, required: true, enum: ['default', 'custom'] }
        };

        super('domain', _model, {
            compressed: { unique: true }
        });
    }
};
