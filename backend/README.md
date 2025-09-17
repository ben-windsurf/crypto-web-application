# Crypto Trading Backend

A Java 17 Spring Boot mock backend for the crypto trading dashboard application.

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
- Java 17 or higher
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

The application uses H2 in-memory database for development. You can access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:cryptodb`
- Username: `sa`
- Password: (empty)

## Configuration

Key configuration options in `application.properties`:
- Server port: `server.port=8080`
- Database URL: `spring.datasource.url=jdbc:h2:mem:cryptodb`
- CORS: Enabled for all origins on `/api/**` endpoints

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
