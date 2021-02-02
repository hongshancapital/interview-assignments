import ID_Service_Repository_Impl from './id_service_repository_impl';
import DB_Provider_MySQL from './db_provider_mysql';
import { RANGE_ID_MAX, SEQUENCE_MAX } from '../config';
import { FALSY_ID, FALSY_RANGE_ID, FALSY_SEQUENCE } from '../constants';

describe('ID_Service_Repository_Impl Unit Tests', () => {
  const db_provider = new DB_Provider_MySQL();
  beforeEach(
    async () => { await db_provider.raw().execute('update ranged_sequence set sequence=0'); },
  );

  it('should succeed with valid range', async () => {
    const id_service_repo = new ID_Service_Repository_Impl(db_provider);
    const res = await id_service_repo.create_one_with_range(25);
    expect(res).toEqual(25 * (SEQUENCE_MAX + 1));
  });

  it('should succeed without preferred range', async () => {
    const id_service_repo = new ID_Service_Repository_Impl(db_provider);
    const res = await id_service_repo.create_one_roughly();
    expect(res).toBeGreaterThanOrEqual(0);
    expect(res).toBeLessThan(RANGE_ID_MAX * (SEQUENCE_MAX + 1));
  });

  it('should fail if a range\'s sequence has reached max', async () => {
    await db_provider.raw().execute('update ranged_sequence set sequence= ? where range_id = ?', [SEQUENCE_MAX, 21]);
    const id_service_repo = new ID_Service_Repository_Impl(db_provider);
    const res = await id_service_repo.create_one_with_range(21);
    expect(res).toEqual(FALSY_ID);
  });

  it('should fail if all range\'s sequence has reached max', async () => {
    await db_provider.raw().execute('update ranged_sequence set sequence= ?', [SEQUENCE_MAX]);
    const id_service_repo = new ID_Service_Repository_Impl(db_provider);
    const res = await id_service_repo.create_one_roughly();
    expect(res).toEqual(FALSY_ID);
  });

  it('should call enter the transactional way', async () => {
    const db_provider_local = new DB_Provider_MySQL();
    const id_service_repo = new ID_Service_Repository_Impl(db_provider_local);
    const spy1 = jest.spyOn(db_provider_local, 'increase_sequence_of_a_range').mockImplementation(() => Promise.resolve(false));
    const spy2 = jest.spyOn(db_provider_local, 'select_a_range_and_increase_its_sequence_transactionally');

    const res = await id_service_repo.create_one_roughly();

    db_provider_local.stop();

    expect(spy1).toBeCalledTimes(1);
    expect(spy2).toBeCalledTimes(1);
    expect(res).not.toEqual(FALSY_ID);
  });

  it('should failed if the transactional operation failed', async () => {
    const db_provider_local = new DB_Provider_MySQL();
    const id_service_repo = new ID_Service_Repository_Impl(db_provider_local);
    const spy1 = jest.spyOn(db_provider_local, 'increase_sequence_of_a_range').mockImplementation(() => Promise.resolve(false));

    const spy2 = jest.spyOn(db_provider_local, 'select_a_range_and_increase_its_sequence_transactionally')
      .mockResolvedValue([FALSY_RANGE_ID, FALSY_SEQUENCE]);

    const res = await id_service_repo.create_one_roughly();
    db_provider_local.stop();

    expect(spy1).toBeCalledTimes(1);
    expect(spy2).toBeCalledTimes(1);
    expect(res).toEqual(FALSY_ID);
  });

  afterAll(async () => {
    await db_provider.stop();
  });
});
