# demo-spring-react-example: dsre


Storybook is here:
* Production: <https://happycows.github.io/demo-spring-react-example-docs/>
* QA:  <https://happycows.github.io/demo-spring-react-example-docs-qa/>


The GitHub actions script to deploy the Storybook to QA requires some configuration; see [docs/github-actions.md](docs/github-actions.md) for details.



# Setup before running application

Before running the application for the first time,
you need to do the steps documented in [`docs/oauth.md`](docs/oauth.md).

Otherwise, when you try to login for the first time, you 
will likely see an error such as:

```
Authorization Error
Error 401: invalid_client
The OAuth client was not found.
```


# Getting Started on localhost

* The backend and frontend should be run separately, so first start by opening two separate terminal windows.
* In the first window, start up the backend with `mvn spring-boot:run`
* In the second window, `cd frontend` then:
  - If running for the first time, do `npm install` to install dependencies.
  - After that, do `npm start`
* Then, the app should be available on <http://localhost:8080>

If it doesn't work at first, e.g. you have a blank page on  <http://localhost:8080>, give it a minute and a few page refreshes.  Sometimes it takes a moment for everything to settle in.

# Getting Started on Heroku

On Heroku, you'll need to set the following configuration variable:

* Using the Heroku CLI:
  ```
  heroku config:set PRODUCTION=true --app <heroku app name>
  ```
* Or set it on the Heroku Dashboard:
  ![image](https://user-images.githubusercontent.com/1119017/149855768-7b56164a-98f7-4357-b877-da34b7bd9ea4.png)

You'll also need to follow the OAuth set up instructions here: [`docs/oauth.md`](docs/oauth.md).

If you get the following message, it probably means that you failed to setup one or more of the environment variables:

```
Failed to connect to the frontend server! You may have forgotten to run npm start in a separate ./dev_environment window (or it hasn't loaded yet).
```

# Accessing swagger

To access the swagger API endpoints, use:

* <http://localhost:8080/swagger-ui/index.html>

# To run React Storybook

* cd into frontend
* use: npm run storybook
* This should put the storybook on http://localhost:6006
* Additional stories are added under frontend/src/stories

* For documentation on React Storybook, see: https://storybook.js.org/

