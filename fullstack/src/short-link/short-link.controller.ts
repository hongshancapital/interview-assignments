import {
  Controller,
  Get,
  Param,
  Post,
  Body,
  Redirect,
  Req,
  NotFoundException,
  BadRequestException,
  InternalServerErrorException,
} from '@nestjs/common';
import { ShortLinkService } from './short-link.service';
import { isValidUrl } from '../utils/reg';

@Controller('')
export class ShortLinkController {
  constructor(private readonly shortLinkService: ShortLinkService) {}
  @Get(':id')
  @Redirect('/', 302)
  async findLink(@Param() params) {
    const id = params.id;
    if (!id || typeof id !== 'string') {
      throw new BadRequestException();
    }
    const linkEntity = await this.shortLinkService.findLinkById(id);
    if (!linkEntity || typeof linkEntity.link !== 'string') {
      throw new NotFoundException();
    } else {
      return {
        url: linkEntity.link,
        statusCode: 302,
      };
    }
  }

  @Post('')
  async createLink(@Body() body, @Req() req) {
    const { link } = body;
    if (typeof link !== 'string') {
      throw new BadRequestException();
    } else if (!isValidUrl(link)) {
      throw new BadRequestException();
    }

    const res = await this.shortLinkService.createLink(link, 3);
    if (res && typeof res === 'string') {
      return {
        statusCode: 201,
        message: 'Success',
        link: `${req.protocol}://${req.headers.host}/${res}`,
      };
    } else {
      throw new InternalServerErrorException();
    }
  }
}
