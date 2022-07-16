import 'dart:math';

import 'package:hs_demo/domain/api/post_repository.dart';
import 'package:hs_demo/domain/model/post.dart';

import '../../domain/data/post_data_provider.dart';

class PostDataMockProvider extends PostDataProvider {
  var data = [
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/c8/54/70/c85470ed-3e6c-f7c4-792e-ef30f4fb5c8d/source/100x100bb.jpg",
        "Google Chat",
        "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/73/df/36/73df361c-aa5a-7498-540a-0a83e57dfdf0/source/100x100bb.jpg",
        "Viber Messenger: Chats & Calls",
        "Viber is the FREE, simple, fast, and secure messaging and calling app. It’s the messenger of choice ",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple112/v4/ca/6c/ea/ca6cea6e-e2d0-ac20-2834-ad85956ad79f/source/100x100bb.jpg",
        "WeChat",
        "WeChat is more than a messaging and social media app – it is a lifestyle for one billion users acros",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/49/3b/71/493b71da-1b17-0531-6999-3247356f678e/source/100x100bb.jpg",
        "Kik Messaging & Chat App",
        "Get connected.Kik is way more than just messaging. It’s the easiest way to connect with your frie",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/87/4f/66/874f6666-d51b-5adb-4abf-89e9f07a6caa/source/100x100bb.jpg",
        "Twitter",
        "Join the conversation! Retweet, chime in on a thread, go viral, or just scroll through the Twitter ",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/74/e8/a1/74e8a1a3-aede-67aa-9e71-1daa00d45cdb/source/100x100bb.jpg",
        "Discord - Chat, Talk & Hangout",
        "Discord is where you can make a home for your communities and friends. Where you can stay close and ",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple126/v4/dd/70/41/dd7041f7-34c7-ef3e-d52b-6576d8389351/source/100x100bb.jpg",
        "Skype",
        "Skype keeps the world talking. Say “hello” with an instant message, voice or video call – all for fr",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/3c/92/28/3c922831-6d19-8728-31a5-a86a7b341e08/source/100x100bb.jpg",
        "Messenger",
        "Be together whenever, with our free* all-in-one communication app, complete with unlimited text, voi",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/d8/0b/f3/d80bf346-ec7d-ed91-cd4b-27d2d5846b6a/source/100x100bb.jpg",
        "Tango-Live Stream & Video Chat",
        "Download Tango now and become part of a social video live stream platform where you can discover tal",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/69/52/1e/69521ec3-de27-a557-68a6-ac954d08d6de/source/100x100bb.jpg",
        "Facebook",
        "Connect with friends, family and people who share the same interests as you. Communicate privately, ",
        isFavorite: false),
    Post(
        "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/68/c1/b9/68c1b9b9-fe0c-38bc-c0cc-2506c9392586/source/100x100bb.jpg",
        "Bumble - Dating. Friends. Bizz",
        "Millions of people have signed up for Bumble to start building valuable relationships, finding frien",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple116/v4/ae/e4/8b/aee48bf2-61a9-e3dc-1412-07d2b4d81d47/source/100x100bb.jpg",
        "LoopChat: College Chats+Social",
        "LoopChat is the #1 plug for all things college: find your class chats, host virtual parties, and mee",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/58/c5/77/58c57765-5eff-fd43-d3ea-1241d967e55d/source/100x100bb.jpg",
        "Marco Polo - Stay in Touch",
        "Marco Polo combines the best of texting, social media and video chats - all in one private, easy to ",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/88/27/c2/8827c2fe-2517-e96e-3875-11fc4a5ca5c7/source/100x100bb.jpg",
        "Snapchat",
        "Snapchat is a fast and fun way to share the moment with your friends and familySNAP • Snapchat op",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/16/9c/7b/169c7bba-8467-b197-f4d6-077c93e79667/source/100x100bb.jpg",
        "Tinder - Dating New People",
        "Welcome to Tinder — you can be here for a good time, and a long time, too. With 30 billion matches t",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/f7/6f/6d/f76f6de1-1fa3-b3ca-e41c-fefd95d7adbd/source/100x100bb.jpg",
        "Badoo - Dating. Chat. Friends",
        "Welcome to Badoo, the place to date honestly!In a society that profits on our self-doubt, Badoo i",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/ef/6b/56/ef6b56ef-ea48-ec2f-fd9c-41e74b4f43fb/source/100x100bb.jpg",
        "Chat & Date: Online Dating App",
        "Chat & Date is the most easy to use dating app to find amazing people near you, and let them find yo",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/68/aa/b6/68aab645-ea35-4132-9ee1-b9e92e937964/source/100x100bb.jpg",
        "Microsoft Teams",
        "Whether you’re working with teammates on a project or planning a weekend activity with loved ones, M",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/6d/4f/99/6d4f9933-c57d-0b39-9d9c-f3d21428fd0a/source/100x100bb.jpg",
        "WhatsApp Messenger",
        "WhatsApp from Facebook is a FREE messaging and video calling app. It’s used by over 2B people in mor",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple112/v4/26/86/7f/26867fda-ba1d-9be6-6782-a08224046178/source/100x100bb.jpg",
        "MeetMe - Meet, Chat & Go Live",
        "MeetMe helps you find new people nearby who share your interests and want to chat now! It’s fun, fri",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/45/ba/c8/45bac82b-c126-6124-92c1-f612db5c34cc/source/100x100bb.jpg",
        "Amino: Communities and Fandom",
        "Explore your interests, tell your story and find your people on Amino.Start by exploring a new type",
        isFavorite: false),
    Post(
        "https://is4-ssl.mzstatic.com/image/thumb/Purple122/v4/bf/85/91/bf85919e-e88e-aa4c-4488-fac6c8815850/source/100x100bb.jpg",
        "ZOOM Cloud Meetings",
        "Zoom is #1 in customer satisfaction and the best unified communication experience on mobile.It's s",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple112/v4/6b/94/6a/6b946a42-8e15-32ec-9a49-c839bfd3bdde/source/100x100bb.jpg",
        "Zoosk - Social Dating App",
        "DOWNLOAD ZOOSK AND GET READY TO MEET NEW PEOPLE!With 40 million members, we are one of the most tr",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/cc/0a/a3/cc0aa3f8-8a2d-6045-2e67-66aae096cedf/source/100x100bb.jpg",
        "Chat Master!",
        "Game consists of several activities related to mobile phone, especially texting. Each chat scenario,",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/a4/e3/6d/a4e36d26-d5f4-d2a8-49f0-8e86d550a23e/source/100x100bb.jpg",
        "LiveMe – Live Stream & Go Live",
        "LiveMe is a popular live streaming social network. It allows you to live stream your special moments",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/4a/4e/c4/4a4ec4f4-ab2f-f5b0-59c4-eb7fc4acf167/source/100x100bb.jpg",
        "IMVU: 3D Avatar Creator & Chat",
        "Enter IMVU, the world’s largest avatar-based social network where shared experiences build deeper fr",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/66/92/8b/66928bb6-7507-ff00-c29c-b88eea1a8d94/source/100x100bb.jpg",
        "BIGO LIVE-Live Stream, Go Live",
        "Watch great live streams, enjoy live game streaming, live chat with people worldwide, go live to be ",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/96/33/d7/9633d747-6f7d-8788-ae67-822187cab041/source/100x100bb.jpg",
        "IRL - Social Messenger",
        "IRL is the place for Social Messaging! Follow your friends, join groups you relate to, and chat! IRL",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/99/fa/04/99fa04db-8a0d-237c-afa8-b92db4f35b02/source/100x100bb.jpg",
        "textPlus: Text Message + Call",
        "Bringing you free texting, free picture messaging, a free US phone number, free inbound phone calls,",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple115/v4/39/4c/ea/394cea11-1046-15b1-6800-bfb382bf27dd/source/100x100bb.jpg",
        "Yarn - Chat & Text Stories",
        "Ever wanted to snoop through people’s conversations and not feel guilty for it? Want to enter a worl",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/fe/a1/9a/fea19a59-271b-30d3-b7fb-8da73b83e516/source/100x100bb.jpg",
        "Plenty of Fish Dating",
        "Plenty of Fish Free Dating (POF) is where singles have more conversations than any other dating app.",
        isFavorite: false),
    Post(
        "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/45/18/da/4518da4b-c187-7a7d-467e-9b784731bccd/source/100x100bb.jpg",
        "Chat for Strangers, Video Chat",
        "Making new friends is now an entertaining iPhone and IPad experience.With Chat for Strangers - Ra",
        isFavorite: false),
    Post(
        "https://is4-ssl.mzstatic.com/image/thumb/Purple126/v4/f5/66/66/f566664a-eb7b-47bb-379b-37600e356bc5/source/100x100bb.jpg",
        "imo video calls and chat HD",
        "Message and video chat with your friends and family for FREE, no matter what device they are on!- ",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/c1/2a/19/c12a197f-a9de-76d7-9875-7a6e16dcd3a0/source/100x100bb.jpg",
        "Telegram Messenger",
        "Pure instant messaging — simple, fast, secure, and synced across all your devices. One of the world'",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple115/v4/ec/6c/5b/ec6c5b39-a349-2a96-5007-fe2cdb6efd4c/source/100x100bb.jpg",
        "GChat - Gay Chat & Dating",
        "GChat is the world’s #1 FREE mobile social networking app for gay, bi, trans, and queer people to co",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/2c/77/b1/2c77b19c-50d6-b4d5-950c-749fcfebc75a/source/100x100bb.jpg",
        "Tagged -Chill, Chat & Go Live!",
        "Tagged is the #1 place to link up with new people, chill and play games with over 300 Million other ",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/fe/a2/d8/fea2d836-ba40-7fad-3eaf-8da7dc385b69/source/100x100bb.jpg",
        "Hooked",
        "Watch thrilling stories on Hooked. Don’t miss these super-popular videos and chats everyone is obses",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple118/v4/87/9e/ad/879eadc5-7b9e-d597-8ff1-38985c7c6b35/source/100x100bb.jpg",
        "Emoji Keyboard - Gif Stickers",
        "Do you tired of boring text SMS or email? And want to impress your friends? Just download  this app!",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/45/5f/5e/455f5ed3-b6d2-9fdc-4437-5bbcdeb01668/source/100x100bb.jpg",
        "Amazon Alexa",
        "Use the Amazon Alexa app to manage Alexa-enabled devices, control music playback, view shopping list",
        isFavorite: false),
    Post(
        "https://is4-ssl.mzstatic.com/image/thumb/Purple122/v4/fd/40/22/fd4022a2-023c-0065-d00d-ed799ab3c4b9/source/100x100bb.jpg",
        "Reddit",
        "Reddit is the place where people come together to have the most authentic and interesting conversati",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple112/v4/cc/14/1e/cc141ed6-29f5-9729-1e41-04465a8c751c/source/100x100bb.jpg",
        "GroupMe",
        "GroupMe - A Home for All the Groups in Your Life.Family. Roommates. Friends. Coworkers. Teams. V",
        isFavorite: false),
    Post(
        "https://is5-ssl.mzstatic.com/image/thumb/Purple116/v4/a4/70/05/a470055c-9056-ebfd-c2a1-3854f425e59b/source/100x100bb.jpg",
        "Grindr - Gay Dating & Chat",
        "Grindr is the world’s #1 FREE mobile social networking app for gay, bi, trans, and queer people to c",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/32/a5/55/32a55564-4288-96a5-d65b-f57f580979b9/source/100x100bb.jpg",
        "Anonymous Chat Rooms, Dating",
        "The best anonymous chat room app with 30,000,000+ people meeting new friends, confessing or searchin",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple126/v4/1c/98/8f/1c988f4c-9577-8bea-4d17-2842758c6682/source/100x100bb.jpg",
        "TextingStory Chat Story Maker",
        "Unleash your creativity! Also read the FAQ down below ;)1. Write a text conversation in TextingSto",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple116/v4/2c/f9/1e/2cf91e27-d9ab-2a9b-6dc7-a203854ba86c/source/100x100bb.jpg",
        "Skout — Meet New People",
        "Skout is the global network for meeting new people.Instantly meet people near you or around the wo",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/10/ef/89/10ef8922-4fc0-188f-1884-1ca994f0e860/source/100x100bb.jpg",
        "Match: Dating & Relationships",
        "Welcome to Match. Here, being real beats playing it cool. Knowing who you are and what you want is a",
        isFavorite: false),
    Post(
        "https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/60/03/00/60030004-13e9-ecb6-45f6-ad77dc4b80cc/source/100x100bb.jpg",
        "Google Duo",
        "Google Duo is the highest quality video calling app*. It’s simple, reliable, and works on smartphone",
        isFavorite: false),
    Post(
        "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/c1/36/8b/c1368b0c-5f7d-60d2-a385-482349b6a51e/source/100x100bb.jpg",
        "TextNow: Call + Text Unlimited",
        "Join the more than 100 million people who phone smarter, with free texting, free calling, and free n",
        isFavorite: false),
    Post(
        "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/f8/4f/a6/f84fa686-413c-d588-d990-105d226da4d3/source/100x100bb.jpg",
        "ZEPETO: 3D avatar, chat & meet",
        "In a space without limits,another me in another universe — ZEPETO.Create who you want to be, and",
        isFavorite: false),
    Post(
        "https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/81/35/88/8135887c-80e0-8c3a-3c26-9da09a0ab06a/source/100x100bb.jpg",
        "Hily Dating - Meet New People",
        "Hey!Did you know that Hily dating is the perfect app to make new relationships happen? If you didn",
        isFavorite: false)
  ];

  final _pageSize = 15;
  final _delayTime =const Duration(seconds: 3);

  @override
  Future<PageablePost> list({int pageIndex = 0}) {
    return Future.delayed(
         _delayTime,
        () => Future.value(PageablePost(
            pageIndex,
            data.length > _pageSize * (1 + pageIndex),
            data.sublist(pageIndex * _pageSize, min((pageIndex + 1) * _pageSize, data.length)))));
  }

  @override
  Future<Post> collect(Post post) {
    final index = data.indexOf(post);
    if (index >= 0) {
      var newPost = post.copy(!post.isFavorite);
      data[index] = newPost;
      return Future.value(newPost);
    }
    return Future.value(post);
  }
}
