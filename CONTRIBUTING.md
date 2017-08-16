# Contributing code to IITP-Connect


This is a living document. If you see something that could be improved, edit this document and submit a pull request following the instructions below!

## Setting up your development environment

1. Download and install [Git](https://git-scm.com/downloads) and add it to your PATH

1. Download and install [Android Studio](https://developer.android.com/studio/index.html) 

1. Fork the IITP-Connect project ([why and how to fork](https://help.github.com/articles/fork-a-repo/))

1. Clone your fork of the project locally. At the command line:

        git clone https://github.com/YOUR-GITHUB-USERNAME/IITP-Connect

 If you prefer not to use the command line, you can use Android Studio to create a new project from version control using `https://github.com/YOUR-GITHUB-USERNAME/IITP-Connect`. 

1. Open the project in the folder of your clone from Android Studio. To run the project, click on the green arrow at the top of the screen. The emulator is very slow so we generally recommend using a physical device when possible.

## Submitting a pull request
To contribute code to **IITP-Connect**, you will need to open a [pull request](https://help.github.com/articles/about-pull-requests/) in the **development** branch which will be reviewed by the community and then merged into the core project.

1. Set up your development environment.  

2. To make sure you have the latest version of the code, set up this repository as [a remote for your fork](https://help.github.com/articles/configuring-a-remote-for-a-fork/) and then [sync your fork](https://help.github.com/articles/syncing-a-fork/).  

3. Create a branch for the code you will be writing:

        git checkout -b NAME_OF_YOUR_BRANCH  

4. If there is an [issue](https://github.com/Njack-IITP/IITP-Connect/issues) corresponding to what you will work on, **comment on it** to say you are addressing it. If there is no issue yet, create one to provide background on the problem you are solving.  

5. Once you've made incremental progress towards you goal, commit your changes with a meaningful commit message. Use [keywords for closing issues](https://help.github.com/articles/closing-issues-via-commit-messages/) to refer to issues and have them automatically close when your changes are merged.

        git commit -m "Do a thing. Fix #1."

6. Push changes to your fork at any time to make them publicly available:

        git push
        
7. Once you have completed your code changes, verify that you have followed the [style guidelines](https://github.com/opendatakit/collect/blob/master/CONTRIBUTING.md#style-guidelines). Additionally, [lint](https://developer.android.com/studio/write/lint.html) is run for each new build so please run `gradle lint` and fix any errors before issuing a pull request.

8. When your changes are ready to be added to the core IITP-Connect project, [open a pull request](https://help.github.com/articles/creating-a-pull-request/). Make sure to open the pull request on the **development** branch. Describe your changes in the comment, refer to any relevant issues using [keywords for closing issues](https://help.github.com/articles/closing-issues-via-commit-messages/) and tag any person you think might need to know about the changes.

9. Pull requests will be reviewed when committers have time. If you haven't received a review in 10 days, you may notify committers by putting `@Njack-IITP/IITP-Connect` in a comment.

## Style guidelines
Follow the [Android style rules](http://source.android.com/source/code-style.html) and the [Google Java style guide](https://google.github.io/styleguide/javaguide.html).

## Strings
Always use [string resources](https://developer.android.com/guide/topics/resources/string-resource.html) instead of literal strings. This ensures wording consistency across the project.  

## Code from external sources
Sites like [StackOverflow](http://stackoverflow.com/) will sometimes provide exactly the code snippet needed to solve a problem. You are encouraged to use such snippets in **IITP-Connect** as long as you attribute them by including a direct link to the source. In addition to complying with the content license, this provides useful context for anyone reading the code.

