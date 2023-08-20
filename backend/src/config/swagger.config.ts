import { DocumentBuilder } from "@nestjs/swagger";

const swaggerConfig = new DocumentBuilder()
    .setTitle("KEYCLOAK")
    .setDescription("LSG API's")
    .setVersion("1.0")
    .addBearerAuth()
    // .addGlobalParameters({
    //     name: 'authorization',
    //     in: "header",
    //     schema: {
    //         default:
    //             "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOTTRJNk1FNkV6czFCWU9XMXRJUDlnd2FjYUQ1UWZMMGs1N0taZ3ZmbFJnIn0.eyJleHAiOjE2OTI1NTIyNDUsImlhdCI6MTY5MjUxNjI0NSwiYXV0aF90aW1lIjoxNjkyNTE2MjQ1LCJqdGkiOiI3NDk3YTM1Zi1lNDg5LTQyODYtOTY4OS0yZGY5ODMzYTU3NjIiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjcwMjAvcmVhbG1zL2dvb2dsZSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI3NTY1MDFlMC03NzVkLTQwOTgtYWM2YS03ODQwYTM1NTk4ZTgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJnb29nbGUtY2xpIiwic2Vzc2lvbl9zdGF0ZSI6IjhiMmMxMWQ2LTIwYTctNDMyMC1iNTM5LTAyNzlhMGFlYzU0NiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDozMDAwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWdvb2dsZSIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiOGIyYzExZDYtMjBhNy00MzIwLWI1MzktMDI3OWEwYWVjNTQ2IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJHb29nbGUgRG90IENvbSIsInByZWZlcnJlZF91c2VybmFtZSI6Imdvb2dsZSIsImdpdmVuX25hbWUiOiJHb29nbGUiLCJmYW1pbHlfbmFtZSI6IkRvdCBDb20ifQ.BLPo21pVeNmKJjfmvZkGO4p8gjQp7y-pDdmGdvQDj4kUpUq2UkUwnUfQZRW0SritUM29ZzNW8Ak6AGdCnqP53TfUtDgdo5GZjb_viVVii5yeyASVRzMfdH-5ZQ7Fe_CQqgYp7_sRRQ_SGLL_KtnAHUyWdcC0zOXrDSh9YvovlNvN25r9RIOdr8xnS7LB-7dR84RBv8pbRvcQ-iJYNJGm4C56-iPIk3DSi4qAJblk2LOQf_KpuTO-7ldxP2rl3v4gUzlaT4ZdPi5gNamwJUGBgoVvFVeggr0wKBeJjaGreQDtlHpuT_N10Za-Nax5MNLjOIssNMNALZ4CqVKYS39Fqw",
    //     },
    // })
    .build();

export default swaggerConfig;