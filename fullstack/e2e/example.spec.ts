import { test, expect } from '@playwright/test';

test('has title', async ({ page }) => {
  await page.goto('http://localhost:8080/');

  // Expect a title "to contain" a substring.
  await expect(page).toHaveTitle("Short URL");
});

test('fail to generate short urls if the original is empty', async ({ page }) => {
  await page.goto('http://localhost:8080/');

  await page.getByRole('button', { name: "Get me one" }).click();

  const body = await page.locator("body");

  await expect(body).toContainText("Invalid url format");
});

test('generates short urls if the original is valid', async ({ page }) => {
    await page.goto('http://localhost:8080/');

    const text = await page.locator("input[type='text']");
    await text.fill("https://163.com");

    await page.getByRole('button', { name: "Get me one" }).click();

    const body = await page.locator("body");

    await expect(body).toContainText("Your short URL:");
});
