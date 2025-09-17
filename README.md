# Crypto Web Application

## Project Overview

This repository hosts a comprehensive web application tailored for cryptocurrency trading and analytics. The platform is designed to provide users with a robust interface to track market trends, manage crypto portfolios, and perform trading operations efficiently. Leveraging modern web technologies and an intuitive user interface, this project serves as a valuable tool for both novice and professional traders.

## Features

- **Interactive Trading Dashboard:** Real-time data visualization and market monitoring.
- **Modular Widgets:** Customizable trading tools to meet various user preferences.
- **Localization:** Multilingual support with seamless switching for a global user base.
- **Responsive UI:** Optimized for consistent performance across devices and screen sizes.
- **Notification System:** Integrated alerts and toasts for user updates and system messages.

## Technology Stack

### Backend Technologies:

- **Java 17** with Spring Boot 2.7.18
- **Spring Data JPA** for database operations
- **H2 Database** (in-memory) for development
- **Maven** for dependency management
- **RESTful APIs** for frontend communication

### Core Frontend Technologies:

- **HTML5**, **CSS3**, **JavaScript** for the structure, styling, and interactivity.
- **jQuery** for DOM manipulation and event handling.
- **Semantic UI** for responsive and visually appealing component design.

### Key Libraries and Plugins:

- **Moment.js:** Date and time manipulation with localization support.
- **Tempus Dominus:** Advanced date-time picker integrated into the platform.
- **Toastr.js:** Elegant notification system for user alerts and messages.

## Setup and Installation

### Prerequisites
- **Java 17** or higher
- Maven 3.6+
- Modern web browser

### Backend Setup
1. **Clone the repository:**
    ```bash
    git clone https://github.com/ben-windsurf/crypto-web-application.git
    ```

2. **Navigate to the project directory:**
    ```bash
    cd crypto-web-application
    ```

3. **Start the backend API server:**
    ```bash
    cd backend
    mvn spring-boot:run
    ```
    The backend API will be available at `http://localhost:8080`

### Frontend Setup
1. **Launch the application** by opening `index.html` in your preferred browser:
    ```bash
    open index.html
    ```
    Or, simply drag the `index.html` file into your browser window.

2. **Alternative: Use a local development server:**
    ```bash
    # Using Python
    python -m http.server 8000
    
    # Using Node.js (if you have http-server installed)
    npx http-server
    ```
    Then access the application at `http://localhost:8000`

## Usage Instructions

The platform provides users with the following functionalities:

- **Market Insights:** Visualize live price charts, track trends, and set custom alerts.
- **Portfolio Management:** Access tools to analyze holdings and strategize trades.
- **Customizable Settings:** Personalize the UI with language preferences, color themes, and widget placements.

## Recent Updates

### Java 17 Migration (September 2024)
The backend has been successfully migrated from Java 8 to Java 17, providing:
- **Performance Improvements**: Enhanced garbage collection with G1GC
- **Security Enhancements**: Latest security patches and improvements
- **Modern Language Features**: Access to Java 9-17 language improvements
- **Long-term Support**: Java 17 LTS ensures extended support lifecycle

**Migration Details:**
- Upgraded from Java 8 to Java 17
- Updated Spring Boot from 2.1.18.RELEASE to 2.7.18 (conservative approach maintaining `javax.*` imports)
- Updated Maven plugins: `maven-compiler-plugin` 3.11.0, `maven-surefire-plugin` 3.0.0
- All tests pass and application functionality verified
- No breaking changes to existing APIs or functionality

## API Endpoints

### Trades
- `GET /api/trades` - Get all trades
- `POST /api/trades` - Create a new trade
- `GET /api/trades/recent` - Get recent trades
- `GET /api/trades/{id}` - Get trade by ID
- `GET /api/trades/symbol/{symbol}` - Get trades by cryptocurrency symbol

### Cryptocurrencies
- `GET /api/v3/cryptocurrencies` - Get all cryptocurrencies
- `GET /api/v3/simple/price` - Get current prices (CoinGecko API format)
- `GET /api/v3/price/{symbol}` - Get price by symbol

### Portfolio
- `GET /api/portfolio` - Get portfolio holdings

### Dashboard
- `GET /api/dashboard/overview` - Get dashboard overview with recent trades, portfolio, and prices

## Database

The application uses H2 in-memory database for development. You can access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:cryptodb`
- Username: `sa`
- Password: (empty)

## Development

### Running Tests
```bash
cd backend
mvn test
```

### Building for Production
```bash
cd backend
mvn clean package
```

## Project Structure

The project is organized into distinct directories:

- **`/backend`**: Spring Boot backend application with Java 17
- **`/plugins`**: External libraries and third-party assets.
- **`/semantic`**: Semantic UI assets including stylesheets, JavaScript, and themes.
- **`/spectrum`**: Resources for advanced color picking and customization.
- **`/web-trader`**: Core files for the trading interface.
- **`/widget`**: Configurations and assets for additional widgets.

## License

This project is released under the MIT License. Refer to the [LICENSE](LICENSE) file for more details.

## Contact Information

For any inquiries or support, feel free to contact us at: [zuhairkhalid229@gmail.com](mailto:zuhairkhalid229@gmail.com).
