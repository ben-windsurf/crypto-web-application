import { test, expect } from '@playwright/test';

test.describe('Crypto Trading Dashboard', () => {
  
  test.beforeEach(async ({ page }) => {
    await page.route('**/api.coingecko.com/api/v3/simple/price*', async route => {
      await route.fulfill({
        status: 200,
        contentType: 'application/json',
        body: JSON.stringify({
          bitcoin: { usd: 43250, usd_24h_change: 2.5 },
          ethereum: { usd: 2680, usd_24h_change: 1.8 },
          cardano: { usd: 0.52, usd_24h_change: -0.8 },
          solana: { usd: 98.50, usd_24h_change: 4.2 }
        })
      });
    });
    
    await page.goto('/');
  });

  test('should load page with correct title', async ({ page }) => {
    await expect(page).toHaveTitle('Crypto Trading Dashboard');
  });

  test('should display main header', async ({ page }) => {
    const header = page.locator('.ui.huge.header');
    await expect(header).toContainText('Crypto Trading Dashboard');
  });

  test('should display all four crypto cards', async ({ page }) => {
    const cards = page.locator('.crypto-card');
    await expect(cards).toHaveCount(4);
  });

  test('should display Bitcoin card with price and change', async ({ page }) => {
    const btcCard = page.locator('.crypto-card').first();
    await expect(btcCard.locator('.header')).toContainText('Bitcoin (BTC)');
    
    await page.waitForTimeout(1000);
    
    const priceText = btcCard.locator('.ui.large.text');
    await expect(priceText).toBeVisible();
    
    const changeLabel = btcCard.locator('.ui.label');
    await expect(changeLabel).toBeVisible();
  });

  test('should display Ethereum card with price and change', async ({ page }) => {
    const ethCard = page.locator('.crypto-card').nth(1);
    await expect(ethCard.locator('.header')).toContainText('Ethereum (ETH)');
    
    await page.waitForTimeout(1000);
    
    const priceText = ethCard.locator('.ui.large.text');
    await expect(priceText).toBeVisible();
    
    const changeLabel = ethCard.locator('.ui.label');
    await expect(changeLabel).toBeVisible();
  });

  test('should display Cardano card with price and change', async ({ page }) => {
    const adaCard = page.locator('.crypto-card').nth(2);
    await expect(adaCard.locator('.header')).toContainText('Cardano (ADA)');
    
    await page.waitForTimeout(1000);
    
    const priceText = adaCard.locator('.ui.large.text');
    await expect(priceText).toBeVisible();
    
    const changeLabel = adaCard.locator('.ui.label');
    await expect(changeLabel).toBeVisible();
  });

  test('should display Solana card with price and change', async ({ page }) => {
    const solCard = page.locator('.crypto-card').nth(3);
    await expect(solCard.locator('.header')).toContainText('Solana (SOL)');
    
    await page.waitForTimeout(1000);
    
    const priceText = solCard.locator('.ui.large.text');
    await expect(priceText).toBeVisible();
    
    const changeLabel = solCard.locator('.ui.label');
    await expect(changeLabel).toBeVisible();
  });

  test('should apply correct color classes based on price change', async ({ page }) => {
    await page.waitForTimeout(1000);
    
    const btcCard = page.locator('.crypto-card').first();
    const btcLabel = btcCard.locator('.ui.label');
    await expect(btcLabel).toHaveClass(/green/);
    
    const adaCard = page.locator('.crypto-card').nth(2);
    const adaLabel = adaCard.locator('.ui.label');
    await expect(adaLabel).toHaveClass(/red/);
  });

  test('should display price chart canvas', async ({ page }) => {
    const chartCanvas = page.locator('#priceChart');
    await expect(chartCanvas).toBeVisible();
    
    const tagName = await chartCanvas.evaluate(el => el.tagName);
    expect(tagName).toBe('CANVAS');
  });

  test('should display Quick Trade panel', async ({ page }) => {
    const tradingPanel = page.locator('.trading-panel').filter({ hasText: 'Quick Trade' });
    await expect(tradingPanel).toBeVisible();
  });

  test('should have cryptocurrency dropdown', async ({ page }) => {
    const dropdown = page.locator('.ui.selection.dropdown');
    await expect(dropdown).toBeVisible();
    
    await dropdown.click();
    
    await expect(page.locator('.item[data-value="btc"]')).toBeVisible();
    await expect(page.locator('.item[data-value="eth"]')).toBeVisible();
    await expect(page.locator('.item[data-value="ada"]')).toBeVisible();
    await expect(page.locator('.item[data-value="sol"]')).toBeVisible();
  });

  test('should have amount and price input fields', async ({ page }) => {
    const inputs = page.locator('input[type="number"]');
    await expect(inputs).toHaveCount(2);
    
    await expect(inputs.first()).toHaveAttribute('placeholder', '0.00');
    await expect(inputs.nth(1)).toHaveAttribute('placeholder', 'Market Price');
  });

  test('should have Buy and Sell buttons', async ({ page }) => {
    const buyButton = page.locator('.ui.green.button').filter({ hasText: 'Buy' });
    const sellButton = page.locator('.ui.red.button').filter({ hasText: 'Sell' });
    
    await expect(buyButton).toBeVisible();
    await expect(sellButton).toBeVisible();
  });

  test('should show loading state when Buy button is clicked', async ({ page }) => {
    const buyButton = page.locator('.ui.green.button').filter({ hasText: 'Buy' });
    
    await buyButton.click();
    
    await expect(buyButton).toHaveClass(/loading/);
    
    await page.waitForTimeout(1500);
  });

  test('should show loading state when Sell button is clicked', async ({ page }) => {
    const sellButton = page.locator('.ui.red.button').filter({ hasText: 'Sell' });
    
    await sellButton.click();
    
    await expect(sellButton).toHaveClass(/loading/);
    
    await page.waitForTimeout(1500);
  });

  test('should display Portfolio section', async ({ page }) => {
    const portfolioPanel = page.locator('.trading-panel').filter({ hasText: 'Portfolio' });
    await expect(portfolioPanel).toBeVisible();
    
    await expect(portfolioPanel.locator('.item').filter({ hasText: 'BTC' })).toBeVisible();
    await expect(portfolioPanel.locator('.item').filter({ hasText: 'ETH' })).toBeVisible();
    
    await expect(portfolioPanel.locator('.ui.statistic')).toBeVisible();
  });

  test('should display Recent Transactions table', async ({ page }) => {
    const transactionsPanel = page.locator('.trading-panel').filter({ hasText: 'Recent Transactions' });
    await expect(transactionsPanel).toBeVisible();
    
    const table = transactionsPanel.locator('table');
    await expect(table).toBeVisible();
    
    await expect(table.locator('th')).toHaveCount(6);
    
    const rows = table.locator('tbody tr');
    await expect(rows).toHaveCount(3);
  });

  test('should handle dropdown selection', async ({ page }) => {
    const dropdown = page.locator('.ui.selection.dropdown');
    await dropdown.click();
    
    await page.locator('.item[data-value="btc"]').click();
    
    const selectedText = dropdown.locator('.text');
    await expect(selectedText).toContainText('Bitcoin');
  });

  test('should allow input in amount field', async ({ page }) => {
    const amountInput = page.locator('input[type="number"]').first();
    
    await amountInput.fill('0.5');
    await expect(amountInput).toHaveValue('0.5');
  });

  test('should allow input in price field', async ({ page }) => {
    const priceInput = page.locator('input[type="number"]').nth(1);
    
    await priceInput.fill('43250');
    await expect(priceInput).toHaveValue('43250');
  });
});
