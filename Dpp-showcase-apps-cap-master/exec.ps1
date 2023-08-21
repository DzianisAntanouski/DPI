$env:CDS_ENV = "production" ; cds build
cf push -f gen/db
cf push -f gen/srv
cf update-service dpi-inst-enablement -c instance-config.json   