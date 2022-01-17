FROM python:3.8
ENV PYTHONUNBUFFERED 1

RUN mkdir /code
WORKDIR /code

COPY ./requirements.txt /code/
RUN pip install --no-cache-dir -i https://pypi.douban.com/simple/ -r requirements.txt \
    && rm -rf requirements.txt

COPY ./compose/web/*.sh /code/
RUN sed -i 's/\r//' gunicorn.sh \
    && chmod +x gunicorn.sh \
    && sed -i 's/\r//' entrypoint.sh \
    && chmod +x entrypoint.sh

ENTRYPOINT ["/bin/bash", "entrypoint.sh"]
