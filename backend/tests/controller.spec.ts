import {Request, Response} from 'express';
import UrlModel, {IUrl} from '../src/app/model';
import {queryUrlById, shortenUrl} from "../src/app/controller";
import {describe, expect, test} from '@jest/globals';

import * as utils from "../src/app/utils"

describe('controller', () => {
    const shortId = '01234567';
    const urlRecord = {
        url: 'https://original-url.com/origin-url',
        shortId: '01234567',
        shortened: 'https://original-url.com/01234567',
    } as unknown as IUrl;

    const newUrlRecord = {
        url: 'https://original-url.com/origin-url',
        shortId: '01234568',
        shortened: 'https://original-url.com/01234568',
    } as unknown as IUrl;
    const body = {
        url: 'https://original-url.com/origin-url',
    };
    const responseBody = {
        url: 'https://original-url.com/origin-url',
        shortened: 'https://original-url.com/01234567'
    }

    let request: Request;
    let response: Response;
    let statusFn: jest.Mock;
    let jsonFn: jest.Mock;

    beforeEach(() => {
        statusFn = jest.fn().mockReturnThis();
        jsonFn = jest.fn().mockReturnThis();

        response = {
            json: jsonFn,
            status: statusFn,
        } as Partial<Response> as Response;

        request = {} as unknown as Request;
        request.params = {shortId};
        request.body = body;
    });

    afterEach(() => {
        jest.resetAllMocks();
    });

    describe('getUrlbyShortId', () => {
        it('should response with status 400 when shortId is not provided', async () => {
            request.params = {};

            await queryUrlById(request, response);

            expect(statusFn).toHaveBeenCalledWith(400);
            expect(jsonFn).toHaveBeenCalledWith({message: 'shortId is not provided'});
        });

        it('should return url information when it exists in DB', async () => {
            jest.spyOn(UrlModel, 'findOne').mockResolvedValue(urlRecord);

            await queryUrlById(request, response);

            expect(UrlModel.findOne).toHaveBeenCalledWith({shortId});
            expect(statusFn).toHaveBeenCalledWith(200);
            expect(jsonFn).toHaveBeenCalledWith(responseBody);
        });

        it('should response with status 400 when shortId not exist in DB', async () => {
            jest.spyOn(UrlModel, 'findOne').mockResolvedValue(undefined);
            await queryUrlById(request, response);

            expect(UrlModel.findOne).toHaveBeenCalledWith({shortId});
            expect(statusFn).toHaveBeenCalledWith(400);
            expect(jsonFn).toHaveBeenCalledWith({message: 'shortId is invalid'});
        });

        it('should response with status 500 when repository throws an error', async () => {
            jest.spyOn(UrlModel, 'findOne').mockRejectedValue(new Error('Timed out!'));
            await queryUrlById(request, response);

            expect(UrlModel.findOne).toHaveBeenCalledWith({shortId});
            expect(statusFn).toHaveBeenCalledWith(500);
            expect(jsonFn).toHaveBeenCalledWith({message: 'Some thing went wrong!'});
        });
    });

    describe('shortenUrl', () => {
        beforeEach(() => {
            jest.spyOn(utils, "validateUrl").mockReturnValue(true)
            jest.spyOn(UrlModel, 'create');
        });

        it('should response with status 400 when url is not provided', async () => {
            request.body = {};
            await shortenUrl(request, response);

            expect(statusFn).toHaveBeenCalledWith(400);
            expect(jsonFn).toHaveBeenCalledWith({message: 'url is not provided'});
        });

        it('should response with status 400 when url is invalid', async () => {
            request.body = { url: "https:///notvalidurl" };

            jest.spyOn(utils, "validateUrl").mockReturnValue(false)

            await shortenUrl(request, response);

            expect(statusFn).toHaveBeenCalledWith(400);
            expect(jsonFn).toHaveBeenCalledWith({message: 'url is invalid'});
        });

        it('should return success with response includes new shortId', async () => {
            const newShortId = newUrlRecord.shortId // '01234568';

            jest.spyOn(utils, "base10to62").mockReturnValueOnce(newShortId)
            jest.spyOn(UrlModel, 'findOne').mockResolvedValueOnce(undefined);
            jest.spyOn(UrlModel, 'create').mockImplementationOnce(async () => newUrlRecord);
            jest.spyOn(UrlModel, 'countDocuments').mockResolvedValue(1);

            await shortenUrl(request, response);

            expect(jest.spyOn(utils, "validateUrl")).toHaveBeenCalledWith(body.url);
            // expect(UrlModel.create).toHaveBeenCalledWith(newUrlRecord);
            expect(statusFn).toHaveBeenCalledWith(200);
            expect(jsonFn).toHaveBeenCalledWith({url: body.url, shortened: newUrlRecord.shortened});
        });
        //
        it('should return success with response includes existed shortId', async () => {
            jest.spyOn(UrlModel, 'findOne').mockResolvedValueOnce(urlRecord);

            await shortenUrl(request, response);

            expect(jest.spyOn(utils, "validateUrl")).toHaveBeenCalledWith(body.url);
            expect(UrlModel.create).not.toHaveBeenCalled();
            expect(UrlModel.findOne).toHaveBeenCalledWith({url: body.url});
            expect(statusFn).toHaveBeenCalledWith(200);
            expect(jsonFn).toHaveBeenCalledWith({url: body.url, shortened: urlRecord.shortened});
        });

        it('should response with status 500 when UrlModelMock.findOne throws an error', async () => {
            jest.spyOn(UrlModel, 'findOne').mockRejectedValue(new Error('Timed out!'));

            await shortenUrl(request, response);

            expect(jest.spyOn(utils, "validateUrl")).toHaveBeenCalledWith(body.url);
            expect(UrlModel.create).not.toHaveBeenCalled();
            expect(UrlModel.findOne).toHaveBeenCalledWith({url: body.url});
            expect(statusFn).toHaveBeenCalledWith(500);
            expect(jsonFn).toHaveBeenCalledWith({message: 'Some thing went wrong!'});
        });

        it('should response with status 500 when UrlModelMock.create throws an error', async () => {
            const newShortId = newUrlRecord.shortId // '01234568';

            jest.spyOn(utils, "base10to62").mockReturnValueOnce(newShortId)

            jest.spyOn(UrlModel, 'findOne').mockResolvedValueOnce(undefined);
            jest.spyOn(UrlModel, 'create').mockImplementation(async () => {
                throw new Error('Timed out!');
            });
            jest.spyOn(UrlModel, 'countDocuments').mockResolvedValue(1);

            await shortenUrl(request, response);
            expect(jest.spyOn(utils, "validateUrl")).toHaveBeenCalledWith(body.url);
            expect(UrlModel.create).toHaveBeenCalled();
            expect(UrlModel.findOne).toHaveBeenCalledWith({url: body.url});
            expect(statusFn).toHaveBeenCalledWith(500);
            expect(jsonFn).toHaveBeenCalledWith({message: 'Some thing went wrong!'});
        });

        it('should response with status 500 when shortId length greater than 8', async () => {
            const newShortId = '012345689';

            jest.spyOn(utils, "base10to62").mockReturnValueOnce(newShortId)
            jest.spyOn(UrlModel, 'findOne').mockResolvedValueOnce(undefined);
            jest.spyOn(UrlModel, 'countDocuments').mockResolvedValue(1);

            await shortenUrl(request, response);

            expect(jest.spyOn(utils, "validateUrl")).toHaveBeenCalledWith(body.url);
            expect(UrlModel.create).not.toHaveBeenCalled();
            expect(UrlModel.findOne).toHaveBeenCalledWith({url: body.url});
            expect(statusFn).toHaveBeenCalledWith(500);
            expect(jsonFn).toHaveBeenCalledWith({message: 'Out of short ids'});
        });
    });
});