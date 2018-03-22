# Assignment - *Project TwitSplit*

**Tweeter** is an android app that allows users to post short messages limited to 50 characters each.

Sometimes, users get excited and write messages longer than 50 characters.

Instead of rejecting these messages, we would like to add a new feature that will split the message into parts and send multiple messages on the user's behalf, all of them meeting the 50 character requirement.


Time spent: **2** days spent in total

## User Stories

The following **required** functionality is completed:

* [x] Allow the user to input and send messages.
* [x] Display the user's messages.
* [x] If a user's input is less than or equal to 50 characters, post it as is.
* [x] If a user's input is greater than 50 characters, split it into chunks that each is less than or equal to 50 characters and post each chunk as a separate message.
* [x] Messages will only be split on whitespace. If the message contains a span of non-whitespace characters longer than 50 characters, display an error.
* [x] Split messages will have a "part indicator" appended to the beginning of each section. In the example above, the message was split into two chunks, so the part indicators read "1/2" and "2/2". Be aware that these count toward the character limit.

The following **bonus** features are implemented:

* [x] Save message as draft function
* [x] Store messages as local, using Realm Database
* [x] Time ago
* [x] Pull to refresh

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/VddssQn.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Open-source libraries used

- [Time Ago](https://github.com/bancek/android-timeago)
- [ButterKnife](http://jakewharton.github.io/butterknife/) 

## License

    Copyright [2017] [hoinganmotti@gmail.com]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
