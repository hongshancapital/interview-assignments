import click
import json
import requests


@click.command()
@click.option('--input', help='input file streams of records, each line for one record in json format')
@click.option('--url', default="https://foo.com/bar", help='the output file in json format')
def main(input: str, url: str) -> None:
    headers = {'Content-Type': 'application/json'}
    with open(input, 'r') as input_stream:
        input_records_list = json.load(input_stream)
        for record in input_records_list:
            r = requests.post(url, json=record, headers=headers, verify=False)
            if not r.status_code == requests.codes.ok:
                r.raise_for_status()


if __name__ == "__main__":
    main()