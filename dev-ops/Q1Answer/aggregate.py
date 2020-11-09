import click
import json


@click.command()
@click.option('--input', help='input file streams of records, each line for one record in json format')
@click.option('--output', help='the output file in json format')
def main(input: str, output: str) -> None:
    with open(input, 'r') as input_stream:
        input_records_list = json.load(input_stream)
        aggr_dict = {}
        for record in input_records_list:
            # consider aggregate scope is date, time window and process name, could be change base on the requirements
            key = "{}-{}-{}-{}-{}-{}".format(*[record[n] for n in ["deviceName", "processId", "processName", "description", "date", "timeWindow"]])
            if key in aggr_dict:
                record2update = aggr_dict[key]
                record2update.update({"numberOfOccurrence": record2update["numberOfOccurrence"] + 1})
            else:
                record.update({"numberOfOccurrence": 1})
                aggr_dict[key] = record
    with open(output, 'w') as output_stream:
        json.dump(list(aggr_dict.values()), output_stream, indent=2)


if __name__ == "__main__":
    main()