# Consuming Github API

It is a small app that will list the most starred Github repos that were created in the last 30 days. You'll be fetching the sorted JSON data directly from the Github API.

## Introduction 

This is my submission to [the Hidden Founders Mobile Coding Challenge](https://github.com/hiddenfounders/mobile-coding-challenge).

## Getting Started

This sample uses the Gradle build system. To build this project, use the "gradlew build" command or use "Import Project" in Android Studio.

## Screenshots

![alt text](https://github.com/aymaneMx/cga/blob/master/screenshots/sc.PNG)

## libraries

1. [Retrofit2](https://square.github.io/retrofit/) 
The library provides a powerful framework for authenticating and interacting with APIs and sending network requests with OkHttp.
This library makes downloading JSON or XML data from a web API fairly straightforward. Once the data is downloaded then it is parsed into a Plain Old Java Object (POJO) which must be defined for each "resource" in the response.

2. [Picasso](http://square.github.io/picasso/)
Picasso is - simply - a central part of any Android app whatsoever. Thank goodness, it is one line of code - no setup, nothing.
the only thing "better than" Picasso is if you move to Volley but I already use retrofit.
