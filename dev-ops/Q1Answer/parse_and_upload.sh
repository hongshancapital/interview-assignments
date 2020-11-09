#!/bin/bash -xe

BIN_DIR=$(dirname $0)

decompressed=$BIN_DIR/../DevOps_interview_data_set
gzfile=$decompressed.gz

if [ -f $decompressed ]; then
  echo "file already decompressed, skipping"
elif [ -f $gzfile ]; then
  gunzip -d $gzfile
else
  echo "ERROR: no input file available"
  exit 1
fi

# setup python environment
if [ ! -f venv/bin/python ]; then
  python3 -mvenv venv
fi

source venv/bin/activate
pip install -r $BIN_DIR/requirements.txt

parsed_records_json=/tmp/parsed_records.json
#python $BIN_DIR/parse.py --logfile $decompressed --output $parsed_records_json
python $BIN_DIR/parse.py --logfile $decompressed --output $parsed_records_json

aggregated_records_json=/tmp/aggregated_records.json
python $BIN_DIR/aggregate.py --input $parsed_records_json --output $aggregated_records_json

#python $BIN_DIR/upload.py $parsed_records_json
python $BIN_DIR/upload.py --input $aggregated_records_json
