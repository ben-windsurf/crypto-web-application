# Security Configuration

## Environment Variables

The following environment variables must be set in production:

### Required
- `JWT_SECRET`: Secret key for JWT token signing (minimum 256 bits / 32 characters)
- `DB_PASSWORD`: Database password
- `CORS_ALLOWED_ORIGINS`: Comma-separated list of allowed origins (e.g., `https://example.com,https://www.example.com`)

### Optional
- `DB_USERNAME`: Database username (defaults to 'sa')
- `JWT_EXPIRATION`: JWT token expiration in milliseconds (defaults to 86400000 = 24 hours)

## Running with Profiles

### Development
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production
```bash
export JWT_SECRET="your-production-secret-key-at-least-32-characters-long"
export DB_PASSWORD="your-strong-database-password"
export CORS_ALLOWED_ORIGINS="https://yourdomain.com"
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Security Features Implemented

1. **Authentication**: JWT-based authentication for API endpoints
2. **CORS**: Restricted to configured origins only
3. **Rate Limiting**: 100 requests per minute per IP address
4. **Security Headers**: X-Frame-Options, X-XSS-Protection, X-Content-Type-Options, HSTS
5. **Input Validation**: Cryptocurrency symbol whitelist and field length limits
6. **H2 Console**: Disabled in production, enabled only in dev profile
7. **Secure Defaults**: Strong password requirements, no debug logging in production

## Public Endpoints

The following endpoints remain public (no authentication required):
- `GET /api/v3/simple/price/**` - Current cryptocurrency prices
- `GET /api/v3/price/**` - Price by symbol
- `GET /api/v3/cryptocurrencies` - List of cryptocurrencies
- `GET /api/dashboard/overview` - Dashboard overview

All other `/api/**` endpoints require JWT authentication.

## Future Improvements

- **Spring Boot Upgrade**: Currently on version 2.1.18 (2020). Recommend upgrading to 2.7.x or 3.x, but this requires:
  - Upgrading Java from 1.8 to 11+ (for Spring Boot 2.7) or 17+ (for Spring Boot 3)
  - Testing for breaking changes
  - Updating dependencies

- **User Management**: Add proper user registration, login, and role-based access control
- **OAuth2**: Consider OAuth2/OpenID Connect for enterprise deployments
- **Database**: Replace H2 with production-grade database (PostgreSQL, MySQL)
