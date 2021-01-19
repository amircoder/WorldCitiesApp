# World Cities App

This is a sample project which shows how to deal with prefix search for large amount of records, such as cities of the world.
To achieve this, I have implemented the [Radix Tree](https://en.wikipedia.org/wiki/Radix_tree) data structure and used it in
an application with a Clean Architecture organization. In this document I will explain the points about the data structure
and architecture.

Table of Contents
-----------------

- [Data Structure and Algorithm](#data-structure-and-algorithm)
  - [Why Radix Tree?](#why-radix-tree)
  - [Implementation and Improvements](#implementation-and-improvements)
- [App Architecture](#app-architecture)
  - [Modules and their dependencies](#modules-and-their-dependencies)
  - [Dependency Injection](#dependency-injection)

Data Structure and Algorithm
----------------------------
According to the task description, the algorithm used for filtering items would be critical part of this project. Therefore,
in this section I will elaborate on what approach is taken and how it could be beneficial.

###  Why Radix Tree?
Based on research that I have done, `RadixTree` is the best data structure for indexing a large set of objects with string keys
in order to retrieve them by a prefix search. `RadixTree` is the compact and space-optimized form of prefix tree which enables
us to find all nodes starting with a prefix string by `O(k)` complexity order, where `k` is the maximum length of indexed keys.
In case of this project dataset, `k` is dramatically lower than `n` (number of keys), which means that the time complexity
of this search is significantly better than linear search.

###  Implementation and Improvements
The `RadixTree`, as the heart of the algorithm, is minimally implemented. Thus, it is only involved basic functionality for this
project such as insert into and search in tree. Other operations like delete a node is not implemented though.
description (keep retrieval sorted by combining heuristic-bfs and dfs travers, insert remained recursive,
implementation is minimal without delete and etc, preprocess, time to load)

App Architecture
----------------
The architecture of this application is based on well-known `Clean Architecture` consists of 5 gradle modules, to separate
business rules as much as possible.  
In terms of of presentation architecture, common `MVVM` pattern is highly adaptable with
the Android development environment, such as `Jetpack` architectural components, so I have chosen `MVVM` for this aim.

###  Modules and their dependencies
As it was mentioned before, the structure of codebase is consisted of 5 gradle modules, 3 pure `Kotlin` library and 2
`Android` module.

![](/static/modules.png)

#### 1. Scope
`scope` is a pure `Kotlin` module and holds only scope `annotation` classes.

#### 2. Model
`model` is a pure `Kotlin` module and consists of business entities which should be accessible in whole codebase.

#### 3. Domain
`domain` is a pure `Kotlin` module. It provides business logic via use-case classes and defines abstraction of repositories to be
implemented in `data` module. In addition, use-case objects work as a bridge between `app` and `data` modules which is the only
possible way for the `app` module to access provided data by the `data` module.

#### 4. Data
`data` is an `Android` library module. It collaborates with the `Android` framework to provide data. All of the concrete classes
are `internal`, so they cannot be exposed to the `app` module. The concrete objects are created by `dagger` and delivered to the
`domain` via the `app` dependency graph.

#### 5. App
`app` is an `Android` application module. It presents user interface and builds a dependency graph to flow objects through different
layers of architecture.

###  Dependency Injection
In order to establish `IoC` in architecture design of the project, `dagger2` is used. There are 3 `dagger` components in the codebase.

![](/static/dagger.png)

- `AppComponent`: to provide app related concrete objects.
- `CityListComponent`: to provide feature related concrete objects for the city list feature.
- `MapViewerComponent`: to provide feature related concrete objects for the map viewer feature.

It is worth mentioning that feature-based components are sub-components of the `AppComponent`, so they can access to app-scoped objects
like `applicationContext`.
