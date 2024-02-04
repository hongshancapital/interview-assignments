import { FindOptionsWhere } from "typeorm"
import { dataSource } from "@/data/datasource"
import { ShortUriEntity } from "@/data/entities/ShortUriEntity"

export const saveShortUriEntity = async (obj: ShortUriEntity) => {
    await dataSource.getRepository(ShortUriEntity).save(obj)
}

export const getShortUriEntity = async (obj: FindOptionsWhere<ShortUriEntity>) => await dataSource.getRepository(ShortUriEntity).findOneBy(obj)
