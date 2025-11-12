# 🎄 Advent of Code 2025

## ⚙️ Setup Guide

This guide will help you quickly configure a new repository from this template for the current year's Advent of Code.

> **❗ IMPORTANT: Template Maintenance**
>
> **Before starting the setup:** Ensure the **[Maintenance Tasks](#maintenance-tasks)** section is completed on the **template repository** to guarantee that all dependencies are up-to-date.

---

### 🚀 1. New Repository Creation

1.  Open the main page of the [template repository](https://github.com/Flashky/advent-of-code-yyyy).
2.  Click **Use this template** and then **Create a new repository**.
3.  Name the new repository (e.g., `advent-of-code-202X`).

### 🔑 2. Token and Secret Configuration

You need to set up two tokens: the **GitHub** token (`PAT_TOKEN`) and the **Codacy** token (`CODACY_PROJECT_TOKEN`).

#### A. 🐙 Personal Access Token (PAT)

The `PAT_TOKEN` is necessary for GitHub Actions (like Dependabot and year update) to function correctly.

1.  **Verify/Create the PAT:**
    * Go to **[Personal access tokens (classic)](https://github.com/settings/tokens)**.
    * If you don't have a token named `PAT_TOKEN` (or similar), click **Generate New Token**, select the `repo` scope, and **copy the generated token**.
    * If an existing `PAT_TOKEN` is close to expiring, click on it, select **regenerate the token**, and **copy the new value**.
2.  **Add the PAT to the new repository:**
    * Go to **Settings / Secrets and variables / Actions**.
    * Create a new repository secret: **Name:** `PAT_TOKEN`, **Secret:** *\[Your Token]*

#### B. 🛡️ Codacy Project Token

Required to send code coverage reports.

1.  **Get the Codacy Token:**
    * Go to [Codacy](https://app.codacy.com) and **add your new repository**.
    * Open **Settings / Coverage** and **copy** the `CODACY_PROJECT_TOKEN` value.
2.  **Add the Secret (x2):** You need to add the token in **two** locations.
    * **Actions:** Go to **Settings / Secrets and variables / Actions**.
        * Create a new secret: **Name:** `CODACY_PROJECT_TOKEN`, **Secret:** *\[Codacy Token]*
    * **Dependabot:** Go to **Settings / Secrets and variables / Dependabot secrets**.
        * Create a new secret: **Name:** `CODACY_PROJECT_TOKEN`, **Secret:** *\[Codacy Token]*

> 💡 **Note:** Setting the secret for Dependabot is crucial to prevent automated Pull Requests from failing the coverage report job.

### 📅 3. Year Update and Permissions

1.  **Configure Workflow Permissions:**
    * Go to **Settings / Actions / General**.
    * Under **Workflow permissions**, enable:
        * **Read and write permissions**.
        * **Allow GitHub Actions to create and approve pull requests**.
2.  **Run "Update year" Workflow:**
    * Go to the **Actions** tab and open the **Update year** workflow.
    * Click **Run workflow**, type the **year** in `yyyy` format (e.g., `2025`), and **Run workflow**.
3.  **Merge the Pull Request:**
    * The workflow will automatically create a Pull Request.
    * **Open it**, verify the year changes are correct, and **merge** it.

> After merging, the repository description, topics, and all `README.md` and `pom.xml` files will be updated with the correct year.

### 💾 4. Data Repository Setup

To privately manage your input files for each day.

1.  **Create Data Repository:**
    * Create a **private** repository using the [advent-of-code-yyyy-data](https://github.com/Flashky/advent-of-code-yyyy-data) template.
2.  **Add as a [Submodule](https://github.blog/2016-02-01-working-with-submodules/):**

```bash
git clone https://github.com/Flashky/advent-of-code-2025.git &&
cd advent-of-code-2025 &&
git submodule add -b master https://github.com/Flashky/advent-of-code-2025-data.git src/test/resources/inputs && 
git push
```

### 🏆 5. Codacy Badges

1.  Go to [Codacy](https://app.codacy.com) and open your repository.
2.  Open **Settings / General** and **copy** both the `code quality` and `coverage` badges.
3.  **Add them** to this `README.md` (replace or add to the top).

### ✅ 6. Final Steps

1.  **Remove all these setup instructions** from the `README.md`.
2.  **Commit and push** the changes.

**Enjoy your new Advent of Code edition!**

---

<a name="maintenance-tasks"></a>
## 🛠️ Maintenance Tasks

> ⚠️ These steps should be performed on the original **template repository** before creating the new repository.

### Update Dependencies

* Go to the [dependency graph](https://github.com/Flashky/advent-of-code-yyyy/network/updates).
* Manually run the following jobs: **`pom.xml`** and **`.github/workflows/build-report.yml`**.
* Review and merge any pending pull requests.

---

- [Day 1](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day01)
- [Day 2](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day02)
- [Day 3](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day03)
- [Day 4](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day04)
- [Day 5](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day05)
- [Day 6](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day06)
- [Day 7](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day07)
- [Day 8](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day08)
- [Day 9](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day09)
- [Day 10](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day10)
- [Day 11](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day11)
- [Day 12](https://github.com/Flashky/advent-of-code-2025/tree/master/src/main/java/com/adventofcode/flashk/day12)

## Cloning this repository

Without data repository:

```bash
git clone https://github.com/Flashky/advent-of-code-2025.git
```

Including data repository:

```bash
git clone https://github.com/Flashky/advent-of-code-2025.git --recurse-submodules
```

## About

- [Advent of Code](https://adventofcode.com/2025/about)
