# Crypto Trading Backend

A Java 8 Spring Boot mock backend for the crypto trading dashboard application.

## Features

- **Cryptocurrency Price API**: Mock endpoints compatible with CoinGecko API format
- **Trading System**: Buy/sell order processing with realistic execution simulation
- **Portfolio Management**: Track holdings, calculate values, and manage positions
- **Transaction History**: Complete audit trail of all trading activities
- **Dashboard Overview**: Consolidated endpoint for frontend data needs

## API Endpoints

### Cryptocurrency Prices
- `GET /api/v3/simple/price` - Get current prices (CoinGecko compatible)
- `GET /api/v3/price/{symbol}` - Get price for specific cryptocurrency
- `GET /api/v3/cryptocurrencies` - Get all tracked cryptocurrencies

### Trading
- `POST /api/trades` - Create new buy/sell order
- `GET /api/trades` - Get all trades
- `GET /api/trades/recent?limit=10` - Get recent trades
- `GET /api/trades/{id}` - Get specific trade
- `GET /api/trades/symbol/{symbol}` - Get trades for specific symbol
- `PUT /api/trades/{id}/cancel` - Cancel pending trade

### Portfolio
- `GET /api/portfolio` - Get current portfolio holdings
- `GET /api/portfolio/total-value` - Get total portfolio value
- `GET /api/portfolio/{symbol}` - Get holding for specific symbol

### Dashboard
- `GET /api/dashboard/overview` - Get complete dashboard data

## Quick Start

### Prerequisites
- Java 8 or higher
- Maven 3.6+

### Running the Application

1. Navigate to the project directory:
```bash
cd crypto-backend
```

2. Run with Maven:
```bash
mvn spring-boot:run
```

3. The API will be available at `http://localhost:8080`

### Sample API Calls

**Get Current Prices:**
```bash
curl "http://localhost:8080/api/v3/simple/price?ids=bitcoin,ethereum,cardano&vs_currencies=usd&include_24hr_change=true"
```

**Create Buy Order:**
```bash
curl -X POST http://localhost:8080/api/trades \
  -H "Content-Type: application/json" \
  -d '{
    "symbol": "bitcoin",
    "type": "BUY",
    "amount": 0.1,
    "price": 43250.00
  }'
```

**Get Portfolio:**
```bash
curl http://localhost:8080/api/portfolio
```

## Database

The application uses H2 in-memory database for development. Data is reset on each restart.

**Note:** H2 console access is now profile-specific (see Security section below).

## Security

**⚠️ IMPORTANT**: This application now requires proper security configuration before deployment.

See [SECURITY.md](SECURITY.md) for detailed security configuration instructions.

### Quick Start for Development

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

The H2 console will be available at `http://localhost:8080/h2-console` (dev profile only):
- JDBC URL: `jdbc:h2:mem:cryptodb`
- Username: `sa`
- Password: (empty in dev profile)

### Production Deployment

```bash
export JWT_SECRET="your-production-secret-key-at-least-32-characters-long"
export DB_PASSWORD="your-strong-database-password"
export CORS_ALLOWED_ORIGINS="https://yourdomain.com"

mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

See `.env.example` for all available environment variables.

### Security Features

- **JWT Authentication**: Protected API endpoints require Bearer token
- **CORS Protection**: Restricted to configured origins only
- **Rate Limiting**: 100 requests per minute per IP
- **Security Headers**: X-Frame-Options, HSTS, X-Content-Type-Options
- **Input Validation**: Cryptocurrency symbol whitelist
- **H2 Console**: Disabled in production

### Public Endpoints (No Authentication Required)

- `GET /api/v3/simple/price/**` - Cryptocurrency prices
- `GET /api/v3/price/**` - Price by symbol
- `GET /api/v3/cryptocurrencies` - List cryptocurrencies
- `GET /api/dashboard/overview` - Dashboard data

All other `/api/**` endpoints require JWT authentication.

## Configuration

Key configuration options in `application.properties`:
- Server port: `server.port=8080`
- Database URL: `spring.datasource.url=jdbc:h2:mem:cryptodb`
- CORS: Configured via `cors.allowed.origins` environment variable
- JWT: Configured via `jwt.secret` environment variable

## Mock Data

The application initializes with sample data:
- Portfolio holdings for Bitcoin and Ethereum
- Historical trade records
- Realistic price movements with volatility

## Architecture

- **Controllers**: REST API endpoints
- **Services**: Business logic and data processing
- **Repositories**: JPA data access layer
- **Models**: Entity classes for database mapping
- **DTOs**: Data transfer objects for API requests/responses
- **Configuration**: CORS and application setup
