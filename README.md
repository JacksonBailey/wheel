Reinventing the wheel
=====================

Introduction
------------

As developers, we often get cravings to rebuild things. We are also often told to "not rebuild the
wheel." There is wisdom in that in the same way there is wisdom in "don't roll your own crypto." If
something exists that works correctly and is complicated to implement then it is better to use that
than to build your own. That advice applies for more critical things though -- not for hobbies or
learning. For low stakes things, why *not* rebuild the wheel? It can be fun. It can be insightful.

Sometimes I ask myself questions like "How would Java's Collections API look if it were designed
with `Optional` existing?" Of course, I understand why they didn't change it once it was added but
it can't stop me from wondering if it might have been a better way to express `b` in `{"a": null}`.
Even as I type this I see problems with the statement. "You want to check if a method returns `null`
*and* if the `Optional` is present? That can't be better!" That's the beauty of this exercise
though; I am not necessarily trying to make better wheels. I am just making wheels that I would've
made if I had the freedom to make them. I'm sure if any seasoned wheel makers see these attempts
they might shake their heads at the foolishness of some of my designs, but perhaps they'll smile
remembering how they too thought doing certain things was a good idea early on when they were naive.

I say all that but this project is quickly turning into me just working on random things I feel like
working on. That's fine. I have a tendency to restart projects from scratch all the time. Having
something of a "monorepo" to keep all my projects in with my preferences will do me well so I don't
waste time bikeshedding.

Goals
-----

In general, these are the goals (in order of importance):

1. To have fun. This should never be something to stress over.
2. To learn. I should come away with a better understanding of why things are the way they are.
3. To make things that are usable. This should be more than just theory.

In general, these are specifically the non-goals (in order of importance):

1. To make something useful. I don't intend to make anything other people have a desire to use.
2. (For now that's it!)

Ideas
-----

For now these are a few of the ideas I have.

- Collections API
- Dependency injection framework
- HTTP controller
- Roll your own crypto
- Database
- Distributed database
- Test framework with tests directly in classes

Licensing
---------

As a whole this repository is licensed under [AGPL-3.0-or-later][agpl]. Each individual subproject
may have different licenses. As an example, the collections project uses [LGPL-3.0-or-later][lgpl].

[agpl]: https://www.gnu.org/licenses/agpl-3.0.txt
[lgpl]: https://www.gnu.org/licenses/lgpl-3.0.txt

Development guide
-----------------

For information for developers working on this (mostly for future reference by me) please see the
[DEV_GUIDE.md](DEV_GUIDE.md) file.
