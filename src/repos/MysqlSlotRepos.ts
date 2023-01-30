import { DataNode } from "../type/DataNode";
import { SlotOffsetDataEntity } from "../entity/SlotOffsetDataEntity";
import { DataNodeEntity } from "../entity/DataNodeEntity";
import "reflect-metadata";
import {AppDataSource} from "../config/DbConnection";

class MysqlSlotRepos {

    public async prefetchOffset(slotName: string, preFetchOffsetSize: number):Promise<number> {
        let slotOffsetDataEntityRepository = AppDataSource.getRepository(SlotOffsetDataEntity);
        let slotdata = await slotOffsetDataEntityRepository
            .createQueryBuilder("SlotOffsetDataEntity")
            // .where("SlotOffsetDataEntity.slotName = :slotName", { slotName: slotName })
            .getOne();

        if (slotdata == null) {
            slotdata = new SlotOffsetDataEntity();
            slotdata.slotName = slotName;
            slotdata.currentOffset = 0;
        }

        let currentOffset:number = slotdata.currentOffset;
        currentOffset = currentOffset + preFetchOffsetSize;
        slotdata.currentOffset = currentOffset;

        await slotOffsetDataEntityRepository.save(slotdata);
        return currentOffset;

    }

    public async insertNode(dataNode: DataNode):Promise<DataNode> {
        let dataNodeEntityRepository = AppDataSource.getRepository(DataNodeEntity);
        let dataNodeEntity:DataNodeEntity = new DataNodeEntity();
        dataNodeEntity.code = dataNode.code;
        dataNodeEntity.md5 = dataNode.md5;
        dataNodeEntity.origin = dataNode.origin;
        dataNodeEntity.slotName = dataNode.slotName;

        try {
            await dataNodeEntityRepository.save(dataNodeEntity);
            dataNode.offset = dataNodeEntity.id;
            return dataNode;
        } catch (error) {
            return await this.getNodeByOrigin(dataNode.slotName, dataNode.origin);
        }
    }

    public async getNodeByOrigin(slotName: string, origin: string): Promise<DataNode | null> {
        let dataNodeEntityRepository = AppDataSource.getRepository(DataNodeEntity);

        let dataNodeEntity = await dataNodeEntityRepository
            .createQueryBuilder("DataNodeEntity")
            .where("DataNodeEntity.origin = :origin", { origin: origin })
            .getOne();

        if (dataNodeEntity == null) {
           return null;
        }
        let dataNode:DataNode = new DataNode(dataNodeEntity.slotName, dataNodeEntity.id, dataNodeEntity.origin, dataNodeEntity.code, dataNodeEntity.md5);
        return dataNode;
    }

    public async getNodeByCode(slotName: string, code: string): Promise<DataNode | null> {

            
        let dataNodeEntityRepository = AppDataSource.getRepository(DataNodeEntity);

        let dataNodeEntity = await dataNodeEntityRepository.createQueryBuilder("DataNodeEntity")
            .where("DataNodeEntity.code = :code", { code: code })
            .getOne();

        if (dataNodeEntity == null) {
           return null;
        }
        let dataNode:DataNode = new DataNode(dataNodeEntity.slotName, dataNodeEntity.id, dataNodeEntity.origin, dataNodeEntity.code, dataNodeEntity.md5);
        return dataNode;
    }

}
export {MysqlSlotRepos}