FROM python:3.8.2

ENV FLASK_APP=app

COPY . /app

WORKDIR /app

RUN pip install -e .

# Unit tests
# RUN pip install pytest && pytest

EXPOSE 5000

CMD [ "flask", "run", "--host=0.0.0.0" ]
