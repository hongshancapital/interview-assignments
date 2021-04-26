from flask import current_app, Flask


app = Flask(__name__)


@app.route('/')
def hello_world():
    current_app.logger.info('Hello, World!')
    return 'Hello, World!'


@app.route('/error')
def error():
    current_app.logger.error('Error!')
    return 'Error!'
