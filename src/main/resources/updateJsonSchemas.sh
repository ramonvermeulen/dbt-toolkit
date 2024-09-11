#!/bin/bash
# this script is purely used to fetch all the dbt-core json schema files
# would make it easy to detect in case the upstream changes for whatever reason

i=1
while true
do
  # Construct the URL
  url="https://schemas.getdbt.com/dbt/manifest/v${i}.json"

  # Check if the file exists
  if wget --spider $url 2>/dev/null; then
    # Use wget to download the file, overwrite if exists
    wget -O "schema/v${i}.json" $url
  else
    echo 'No more schemas to download, stopped at version ' $i
    break
  fi

  # Increment the counter
  i=$((i + 1))
done