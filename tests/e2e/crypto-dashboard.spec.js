const { test, expect } = require('@playwright/test');

test.describe('Crypto Trading Dashboard - Core Functionality', () => {
  
  test.beforeEach(async ({ page }) => {
    // Navigate to the dashboard before each test
    await page.goto('/');
    // Wait for the page to be loaded and key elements to be visible
    await page.waitForLoadState('domcontentloaded');
    await page.waitForSelector('.ui.huge.header', { timeout: 10000 });
  });

  test('1. Page loads with correct title and header', async ({ page }) => {
    // Check page title
    await expect(page).toHaveTitle('Crypto Trading Dashboard');
    
    // Check main header is visible and contains correct text
    const header = page.locator('.ui.huge.header');
    await expect(header).toBeVisible();
    await expect(header).toContainText('Crypto Trading Dashboard');
    
    // Check Bitcoin icon is present in header
    const bitcoinIcon = header.locator('.bitcoin.icon');
    await expect(bitcoinIcon).toBeVisible();
  });

  test('2. All four cryptocurrency cards are displayed', async ({ page }) => {
    // Check that exactly 4 crypto cards are present
    const cryptoCards = page.locator('.crypto-card');
    await expect(cryptoCards).toHaveCount(4);
    
    // Check each card has the expected cryptocurrency
    const expectedCryptos = ['Bitcoin (BTC)', 'Ethereum (ETH)', 'Cardano (ADA)', 'Solana (SOL)'];
    
    for (let i = 0; i < expectedCryptos.length; i++) {
      const card = cryptoCards.nth(i);
      await expect(card.locator('.header')).toContainText(expectedCryptos[i]);
      
      // Check each card has a price element
      await expect(card.locator('.ui.large.text')).toBeVisible();
      
      // Check each card has a change label
      await expect(card.locator('.ui.label')).toBeVisible();
    }
  });

  test('3. Trading panel form elements are present and functional', async ({ page }) => {
    // Check Quick Trade header
    const tradingHeader = page.locator('h3:has-text("Quick Trade")');
    await expect(tradingHeader).toBeVisible();
    
    // Check cryptocurrency dropdown
    const dropdown = page.locator('.ui.selection.dropdown');
    await expect(dropdown).toBeVisible();
    
    // Click dropdown to open it
    await dropdown.click();
    
    // Check all cryptocurrency options are present
    const dropdownItems = page.locator('.dropdown .menu .item');
    await expect(dropdownItems).toHaveCount(4);
    
    // Check specific options
    await expect(dropdownItems.filter({ hasText: 'Bitcoin (BTC)' })).toBeVisible();
    await expect(dropdownItems.filter({ hasText: 'Ethereum (ETH)' })).toBeVisible();
    await expect(dropdownItems.filter({ hasText: 'Cardano (ADA)' })).toBeVisible();
    await expect(dropdownItems.filter({ hasText: 'Solana (SOL)' })).toBeVisible();
    
    // Close dropdown by clicking elsewhere
    await page.click('body');
  });

  test('4. Amount and price input fields are functional', async ({ page }) => {
    // Check amount input field
    const amountInput = page.locator('input[placeholder="0.00"]');
    await expect(amountInput).toBeVisible();
    await expect(amountInput).toBeEditable();
    
    // Test typing in amount field
    await amountInput.fill('1.5');
    await expect(amountInput).toHaveValue('1.5');
    
    // Check price input field
    const priceInput = page.locator('input[placeholder="Market Price"]');
    await expect(priceInput).toBeVisible();
    await expect(priceInput).toBeEditable();
    
    // Test typing in price field
    await priceInput.fill('50000');
    await expect(priceInput).toHaveValue('50000');
  });

  test('5. Buy and Sell buttons are present and clickable', async ({ page }) => {
    // Check Buy button
    const buyButton = page.locator('.ui.green.button:has-text("Buy")');
    await expect(buyButton).toBeVisible();
    await expect(buyButton).toContainText('Buy');
    
    // Check Sell button
    const sellButton = page.locator('.ui.red.button:has-text("Sell")');
    await expect(sellButton).toBeVisible();
    await expect(sellButton).toContainText('Sell');
    
    // Test Buy button click (should add loading state)
    await buyButton.click();
    await expect(buyButton).toHaveClass(/loading/);
    
    // Wait for loading to complete
    await page.waitForTimeout(1100);
    await expect(buyButton).not.toHaveClass(/loading/);
  });

  test('6. Portfolio section displays correctly', async ({ page }) => {
    // Check Portfolio header
    const portfolioHeader = page.locator('h3:has-text("Portfolio")');
    await expect(portfolioHeader).toBeVisible();
    
    // Check portfolio items
    const portfolioItems = page.locator('.ui.relaxed.divided.list .item');
    await expect(portfolioItems).toHaveCount(3);
    
    // Check Bitcoin holding
    const btcItem = portfolioItems.first();
    await expect(btcItem.locator('.header')).toContainText('0.5 BTC');
    await expect(btcItem.locator('.description')).toContainText('$21,625.00');
    
    // Check Ethereum holding
    const ethItem = portfolioItems.nth(1);
    await expect(ethItem.locator('.header')).toContainText('2.3 ETH');
    await expect(ethItem.locator('.description')).toContainText('$6,164.00');
    
    // Check Cash holding
    const cashItem = portfolioItems.nth(2);
    await expect(cashItem.locator('.header')).toContainText('Cash');
    await expect(cashItem.locator('.description')).toContainText('$5,250.00');
    
    // Check total portfolio value
    const totalValue = page.locator('.ui.statistic .value');
    await expect(totalValue).toContainText('$33,039');
  });

  test('7. Price chart canvas is present', async ({ page }) => {
    // Check Price Chart header
    const chartHeader = page.locator('h3:has-text("Price Chart")');
    await expect(chartHeader).toBeVisible();
    
    // Check chart canvas element
    const chartCanvas = page.locator('#priceChart');
    await expect(chartCanvas).toBeVisible();
    
    // Check chart description
    const chartDescription = page.locator('text=Real-time market data visualization');
    await expect(chartDescription).toBeVisible();
  });

  test('8. Recent transactions table displays correctly', async ({ page }) => {
    // Check Recent Transactions header
    const transactionsHeader = page.locator('h3:has-text("Recent Transactions")');
    await expect(transactionsHeader).toBeVisible();
    
    // Check table is present
    const transactionsTable = page.locator('.ui.celled.striped.table');
    await expect(transactionsTable).toBeVisible();
    
    // Check table headers
    const headers = ['Date', 'Type', 'Asset', 'Amount', 'Price', 'Status'];
    for (const header of headers) {
      await expect(page.locator(`th:has-text("${header}")`)).toBeVisible();
    }
    
    // Check that there are transaction rows
    const transactionRows = page.locator('tbody tr');
    await expect(transactionRows).toHaveCount(3);
    
    // Check first transaction details
    const firstRow = transactionRows.first();
    await expect(firstRow.locator('td').first()).toContainText('2025-09-01 14:30');
    await expect(firstRow.locator('.ui.green.label')).toContainText('Buy');
    await expect(firstRow.locator('td:has-text("BTC")')).toBeVisible();
  });

  test('9. Dropdown selection functionality works', async ({ page }) => {
    // Open cryptocurrency dropdown
    const dropdown = page.locator('.ui.selection.dropdown');
    await dropdown.click();
    
    // Select Bitcoin
    const bitcoinOption = page.locator('.dropdown .menu .item[data-value="btc"]');
    await bitcoinOption.click();
    
    // Check that Bitcoin is selected (dropdown should show Bitcoin)
    await expect(dropdown.locator('.text')).toContainText('Bitcoin (BTC)');
    
    // Open dropdown again and select Ethereum
    await dropdown.click();
    const ethereumOption = page.locator('.dropdown .menu .item[data-value="eth"]');
    await ethereumOption.click();
    
    // Check that Ethereum is now selected
    await expect(dropdown.locator('.text')).toContainText('Ethereum (ETH)');
  });

  test('10. Responsive layout and CSS styling verification', async ({ page }) => {
    // Check main container has correct styling
    const mainContainer = page.locator('.ui.container.main-container');
    await expect(mainContainer).toBeVisible();
    
    // Check crypto cards have correct styling classes
    const cryptoCards = page.locator('.crypto-card');
    for (let i = 0; i < 4; i++) {
      const card = cryptoCards.nth(i);
      await expect(card).toHaveClass(/crypto-card/);
    }
    
    // Check trading panels have correct styling
    const tradingPanels = page.locator('.trading-panel');
    await expect(tradingPanels).toHaveCount(3); // Quick Trade, Portfolio, Recent Transactions
    
    // Check grid layout is present
    const gridLayout = page.locator('.ui.stackable.grid');
    await expect(gridLayout).toBeVisible();
    
    // Check that price elements have appropriate color classes
    const priceElements = page.locator('.ui.large.text');
    await expect(priceElements.first()).toBeVisible();
    
    // Verify semantic UI classes are applied
    const semanticElements = page.locator('.ui');
    await expect(semanticElements).toHaveCount.greaterThan(10);
    
    // Check icons are present
    const icons = page.locator('.icon');
    await expect(icons).toHaveCount.greaterThan(5);
  });

});
