# Hvordan Gikk Quizen Backend

## 🔗 Useful Links

### Localhost

- **Swagger UI:** http://localhost:8080/swagger-ui/index.html

### Production

- **Swagger UI:** https://api.hvordangikkquizen.no/swagger-ui/index.html

## Environments

### .env

There are two .env files, one for local development and one for production:

* `.env.local`
* `.env.prod`

They both look like this:
```dotenv
GOOGLE_CLIENT_ID=<id>
GOOGLE_CLIENT_SECRET=<secret>
DATABASE_URL=<url>
DATABASE_USERNAME=<username>
DATABASE_PASSWORD=<password>
SLACK_WEBHOOK_URL=<url>
```
