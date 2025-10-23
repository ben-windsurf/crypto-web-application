---
description: Run Playwright tests and address any failing ones
auto_execution_mode: 3
---

# Playwright Testing Workflow

This workflow guides you through running Playwright end-to-end tests for the crypto web application and addressing any failures.

## Prerequisites

1. **Ensure dependencies are installed**
```bash
npm install
```

2. **Install Playwright browsers** (if not already installed)
```bash
npx playwright install
```

## Step 1: Start the Application Server

Before running tests, start the local development server:

// turbo
```bash
npm run dev
```

This will start the application on `http://localhost:8080`. Keep this terminal running.

## Step 2: Update Playwright Configuration

Update the `playwright.config.js` to use the correct base URL:

1. Open `playwright.config.js`
2. Uncomment and update the `baseURL` in the `use` section:
```javascript
use: {
  baseURL: 'http://localhost:8080',
  trace: 'on-first-retry',
}
```

3. Uncomment and configure the `webServer` section:
```javascript
webServer: {
  command: 'npm run dev',
  url: 'http://localhost:8080',
  reuseExistingServer: !process.env.CI,
}
```

## Step 3: Run Playwright Tests

### Run all tests in headless mode:
```bash
npx playwright test
```

### Run tests with UI (for debugging):
```bash
npx playwright test --ui
```

### Run tests in headed mode (see browser):
```bash
npx playwright test --headed
```

### Run specific test file:
```bash
npx playwright test tests/example.spec.js
```

## Step 4: Analyze Test Results

1. **View HTML Report** (if tests fail):
```bash
npx playwright show-report
```

2. **Check test output** in terminal for specific error messages

3. **View traces** for failed tests (automatically collected on retry)

## Step 5: Address Common Test Failures

### A. Update Example Tests for Crypto App

The current `example.spec.js` tests Playwright's website. Update it for your crypto app:

1. **Replace the existing tests** with crypto app-specific tests:
```javascript
// @ts-check
import { test, expect } from '@playwright/test';

test('crypto app loads successfully', async ({ page }) => {
  await page.goto('/');
  
  // Check if the main title is present
  await expect(page).toHaveTitle(/Crypto/);
  
  // Verify main navigation elements are visible
  await expect(page.locator('nav')).toBeVisible();
});

test('cryptocurrency data displays', async ({ page }) => {
  await page.goto('/');
  
  // Wait for crypto data to load
  await page.waitForSelector('[data-testid="crypto-price"]', { timeout: 10000 });
  
  // Verify price data is displayed
  await expect(page.locator('[data-testid="crypto-price"]')).toBeVisible();
});

test('portfolio section is accessible', async ({ page }) => {
  await page.goto('/');
  
  // Check if portfolio section exists
  const portfolioSection = page.locator('[data-testid="portfolio"]');
  if (await portfolioSection.count() > 0) {
    await expect(portfolioSection).toBeVisible();
  }
});
```

### B. Handle API Dependencies

If tests fail due to external API calls:

1. **Mock API responses** in tests:
```javascript
test('handles API failures gracefully', async ({ page }) => {
  // Mock failed API response
  await page.route('**/api/v3/simple/price*', route => {
    route.fulfill({
      status: 500,
      body: JSON.stringify({ error: 'API unavailable' })
    });
  });
  
  await page.goto('/');
  
  // Verify error handling
  await expect(page.locator('[data-testid="error-message"]')).toBeVisible();
});
```

2. **Add test data attributes** to your HTML elements for reliable selection

### C. Fix Timing Issues

If tests fail due to timing:

1. **Use proper waits**:
```javascript
// Wait for specific elements
await page.waitForSelector('[data-testid="crypto-list"]');

// Wait for network requests
await page.waitForLoadState('networkidle');

// Wait for specific conditions
await expect(page.locator('[data-testid="price"]')).toBeVisible();
```

### D. Handle Dynamic Content

For cryptocurrency price updates:

1. **Test with stable selectors**:
```javascript
test('price updates work', async ({ page }) => {
  await page.goto('/');
  
  // Get initial price
  const initialPrice = await page.locator('[data-testid="btc-price"]').textContent();
  
  // Trigger refresh or wait for update
  await page.click('[data-testid="refresh-button"]');
  
  // Wait for price to potentially change
  await page.waitForTimeout(2000);
  
  // Verify price element still exists (content may change)
  await expect(page.locator('[data-testid="btc-price"]')).toBeVisible();
});
```

## Step 6: Create Comprehensive Test Suite

Create additional test files for different features:

### A. Navigation Tests (`tests/navigation.spec.js`)
```javascript
import { test, expect } from '@playwright/test';

test.describe('Navigation', () => {
  test('main navigation works', async ({ page }) => {
    await page.goto('/');
    
    // Test navigation between sections
    await page.click('[data-testid="portfolio-nav"]');
    await expect(page.locator('[data-testid="portfolio-section"]')).toBeVisible();
    
    await page.click('[data-testid="market-nav"]');
    await expect(page.locator('[data-testid="market-section"]')).toBeVisible();
  });
});
```

### B. Form Tests (`tests/forms.spec.js`)
```javascript
import { test, expect } from '@playwright/test';

test.describe('Forms', () => {
  test('portfolio form submission', async ({ page }) => {
    await page.goto('/');
    
    // Fill portfolio form
    await page.fill('[data-testid="crypto-symbol"]', 'BTC');
    await page.fill('[data-testid="amount"]', '0.5');
    await page.click('[data-testid="add-to-portfolio"]');
    
    // Verify addition
    await expect(page.locator('[data-testid="portfolio-item"]')).toContainText('BTC');
  });
});
```

## Step 7: Run Tests in CI/CD

Add to your GitHub Actions workflow (`.github/workflows/playwright.yml`):

```yaml
name: Playwright Tests
on:
  push:
    branches: [ main, master ]
  pull_request:
    branches: [ main, master ]
jobs:
  test:
    timeout-minutes: 60
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v4
    - uses: actions/setup-node@v4
      with:
        node-version: lts/*
    - name: Install dependencies
      run: npm ci
    - name: Install Playwright Browsers
      run: npx playwright install --with-deps
    - name: Run Playwright tests
      run: npx playwright test
    - uses: actions/upload-artifact@v4
      if: always()
      with:
        name: playwright-report
        path: playwright-report/
        retention-days: 30
```

## Step 8: Debug Failed Tests

### A. Use Playwright Inspector
```bash
npx playwright test --debug
```

### B. Generate and view traces
```bash
npx playwright test --trace on
npx playwright show-trace trace.zip
```

### C. Take screenshots on failure
Add to `playwright.config.js`:
```javascript
use: {
  screenshot: 'only-on-failure',
  video: 'retain-on-failure',
}
```

## Step 9: Maintenance and Best Practices

1. **Regular Updates**: Keep Playwright updated
```bash
npm update @playwright/test
npx playwright install
```

2. **Test Organization**: Group related tests in describe blocks

3. **Page Object Model**: For complex apps, consider using page objects

4. **Parallel Execution**: Configure workers for faster test runs

5. **Test Data**: Use fixtures for consistent test data

## Troubleshooting Common Issues

### Issue: Tests timeout
**Solution**: Increase timeout in config or use proper waits

### Issue: Element not found
**Solution**: Add data-testid attributes and use reliable selectors

### Issue: API rate limiting
**Solution**: Mock external API calls in tests

### Issue: Flaky tests
**Solution**: Use proper waits and avoid hard-coded delays

## Success Criteria

✅ All tests pass consistently  
✅ Tests cover main user flows  
✅ CI/CD pipeline runs tests automatically  
✅ Test reports are generated and accessible  
✅ Failed tests provide clear debugging information  

Run this workflow whenever you add new features or modify existing functionality to ensure your crypto web application remains stable and functional.