# El Pais Automation Script

This project automates the extraction of articles from the [El Pais Opinion section](https://elpais.com/opinion/), 
translates their titles from Spanish to English using a RapidAPI translation service, and analyzes repeated words in the translated titles. 
It also downloads cover images (if available) and runs cross-browser tests using BrowserStack Automate.

Tech Stack

- **Java 17**
- **Selenium WebDriver**
- **Maven**
- **Edge browser**
- **BrowserStack Automate**
- **RapidAPI Translation API**

How to Run Locally

1. **Clone the repo**
   ```bash
   git clone https://github.com/<your-username>/<repo-name>.git
   cd ElPais
2. - Install dependencies Make sure Maven is installed and run

3. Run the src/main/java --> **Main.java** class as a JAVA Application

4. To run tests on BrowserStack:- Login via CLI -->
browserstack-cli login

5. - Run parallel tests -->
browserstack-cli automate

6. Features-
- ✅ Scrapes up to 5 articles from El País Opinion section
- ✅ Extracts Spanish titles and full article content
- ✅ Downloads cover images (if present)
- ✅ Translates titles to English using RapidAPI
- ✅ Analyzes repeated words in translated titles
- ✅ Runs cross-browser tests via BrowserStack

The core Selenium automation is fully functional and tested locally using Edge browser.

**The working Screenshots are captured with Successful console output and Maven Test success for pom.xml for Browserstack Cross browser testing and uploaded in the Screenshots folder created along with Main Project folder**

--- AUTHOR ---
Aniruddh A Hanumasagar.





