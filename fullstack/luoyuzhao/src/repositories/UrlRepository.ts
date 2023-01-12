
import { Service } from 'typedi';
import UrlEntity from '../model/UrlEntity';
import DataRepository from './DataRepository';

@Service()
class UrlRepository extends DataRepository {
    private readonly DATA_NUMBER_KEY = "DATA_NUM"

    async addUrlData(url: string): Promise<UrlEntity> {
        let id= await this.increase(this.DATA_NUMBER_KEY);
        let data = new UrlEntity(url, id);
        this.set(data.md5, data.short);
        this.set(data.short, data.long);
        return data;
    }

}
export default UrlRepository;