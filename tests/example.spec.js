// @ts-check
import { test, expect } from '@playwright/test';

test('crypto app loads successfully', async ({ page }) => {
  await page.goto('/');
  
  // Check if the main title is present
  await expect(page).toHaveTitle(/Crypto Trading Dashboard/);
  
  // Verify main header is visible
  await expect(page.locator('text=Crypto Trading Dashboard')).toBeVisible();
  
  // Check that the bitcoin icon is present in the header
  await expect(page.locator('.bitcoin.icon')).toBeVisible();
});

test('cryptocurrency cards display correctly', async ({ page }) => {
  await page.goto('/');
  
  // Wait for the page to load
  await page.waitForLoadState('networkidle');
  
  // Verify all four crypto cards are present
  const cryptoCards = page.locator('.crypto-card');
  await expect(cryptoCards).toHaveCount(4);
  
  // Check specific cryptocurrency names
  await expect(page.locator('text=Bitcoin (BTC)')).toBeVisible();
  await expect(page.locator('text=Ethereum (ETH)')).toBeVisible();
  await expect(page.locator('text=Cardano (ADA)')).toBeVisible();
  await expect(page.locator('text=Solana (SOL)')).toBeVisible();
  
  // Verify price elements are visible (they should contain $ signs)
  const priceElements = page.locator('.ui.large.text');
  await expect(priceElements.first()).toBeVisible();
});

test('trading panel is accessible', async ({ page }) => {
  await page.goto('/');
  
  // Check if trading panel exists
  await expect(page.locator('text=Quick Trade')).toBeVisible();
  
  // Verify dropdown for cryptocurrency selection
  await expect(page.locator('.ui.selection.dropdown')).toBeVisible();
  
  // Check buy and sell buttons
  await expect(page.locator('.ui.green.button', { hasText: 'Buy' })).toBeVisible();
  await expect(page.locator('.ui.red.button', { hasText: 'Sell' })).toBeVisible();
});

test('portfolio section displays', async ({ page }) => {
  await page.goto('/');
  
  // Check if portfolio section exists
  await expect(page.locator('text=Portfolio')).toBeVisible();
  
  // Verify portfolio items are visible
  await expect(page.locator('text=0.5 BTC')).toBeVisible();
  await expect(page.locator('text=2.3 ETH')).toBeVisible();
  await expect(page.locator('text=Cash')).toBeVisible();
  
  // Check total portfolio value
  await expect(page.locator('.ui.statistic .value')).toBeVisible();
});

test('price chart is present', async ({ page }) => {
  await page.goto('/');
  
  // Check if chart section exists
  await expect(page.locator('text=Price Chart')).toBeVisible();
  
  // Verify canvas element for chart is present
  await expect(page.locator('#priceChart')).toBeVisible();
});

test('recent transactions table displays', async ({ page }) => {
  await page.goto('/');
  
  // Check if transactions section exists
  await expect(page.locator('text=Recent Transactions')).toBeVisible();
  
  // Verify table headers
  await expect(page.locator('th', { hasText: 'Date' })).toBeVisible();
  await expect(page.locator('th', { hasText: 'Type' })).toBeVisible();
  await expect(page.locator('th', { hasText: 'Asset' })).toBeVisible();
  await expect(page.locator('th', { hasText: 'Amount' })).toBeVisible();
  await expect(page.locator('th', { hasText: 'Price' })).toBeVisible();
  await expect(page.locator('th', { hasText: 'Status' })).toBeVisible();
  
  // Check that transaction rows exist
  const transactionRows = page.locator('tbody tr');
  await expect(transactionRows).toHaveCount(3);
});

test('buy button interaction works', async ({ page }) => {
  await page.goto('/');
  
  // Wait for page to load
  await page.waitForLoadState('networkidle');
  
  // Click the buy button
  const buyButton = page.locator('.ui.green.button', { hasText: 'Buy' });
  await buyButton.click();
  
  // Verify button shows loading state (briefly)
  await expect(buyButton).toHaveClass(/loading/);
});

test('handles API failures gracefully', async ({ page }) => {
  // Mock failed API response
  await page.route('**/api/v3/simple/price*', route => {
    route.fulfill({
      status: 500,
      body: JSON.stringify({ error: 'API unavailable' })
    });
  });
  
  await page.goto('/');
  
  // The page should still load even if API fails
  await expect(page).toHaveTitle(/Crypto Trading Dashboard/);
  await expect(page.locator('text=Crypto Trading Dashboard')).toBeVisible();
});
