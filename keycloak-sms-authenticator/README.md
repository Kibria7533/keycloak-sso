# Keycloak SMS OTP Authenticator

## Build

To build the distribution package, run

```bash
./scripts/package
```

this will produce `./target/keycloak-sms-authenticator-ng.tar.gz` which
you can directly extract into the root of your keycloak installation.

## Supported SMS Service Providers

- [Amazon SNS](https://aws.amazon.com/sns/)
- [GoSMS](https://gosms.eu/)
- [Infobip](https://www.infobip.com/)
- [Twilio](https://www.twilio.com/)

## Configuration

Configure your REALM to use the SMS Authentication.
First create a new REALM (or select a previously created REALM).

Under Authentication > Flows:
* Copy the 'Browser' flow to 'Browser with SMS' flow
* Click on 'Actions > Add execution' on the 'Browser with SMS Forms' line and add the 'SMS OTP'
* Set 'SMS Authentication' to 'REQUIRED' or 'ALTERNATIVE'
* To configure the SMS Authenticator, click 'Actions > Config' and fill in the relevant attributes

Under Authentication > Bindings:
* Select 'Browser with SMS' as the 'Browser Flow' for the REALM.

Under Authentication > Required Actions:
* Click on the 'Register' button and select 'SMS OTP Phone Number' to add the Required Action to the REALM.
* Make sure that for 'SMS OTP Phone Number' has 'Enabled' checkbox set and 'Default Action' checkbox unset.
