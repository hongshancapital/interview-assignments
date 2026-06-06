//
//  AppPageView.swift
//  IAppDemo
//
//  Created by lee on 2023/3/2.
//

import Foundation
import SwiftUI


struct AppPageView: View {
    
    @EnvironmentObject var viewModel: AppListViewModel
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.isFirstLoading {
                    ProgressView()
                        .offset(x: 0, y: -70)
                        .foregroundColor(SwiftUI.Color.red)
                } else {
                    List(viewModel.list) { model in
                        ForEach(viewModel.list) { model in
                            ListItemView(model: model) { itemModel in
                                // modify struct model
                                var updatedModel = itemModel
                                viewModel.updateFav(&updatedModel)
                            }
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 8, trailing: 0))
                            .listRowBackground(Color.clear)
                        }
                        if !viewModel.list.isEmpty {
                            LoadMoreView(noMoreData: viewModel.noMoreData) {
                                await viewModel.loadMore()
                            }
                            .listRowBackground(Color.clear)
                        }
                    }.refreshable {
                        await viewModel.refresh()
                    }
                }
            }
            .navigationTitle("App")
        }
        .navigationViewStyle(.stack)
        .alert(viewModel.alertMessage,
               isPresented: $viewModel.showError,
               actions: {
            // Add Alert Actions
           
        })
        .task { await viewModel.firstLoad() }
    }
}

struct AppPageView_Previews: PreviewProvider {
    static var previews: some View {
        let model1 = ListItemModel(
            trackId: 304878510,
            trackName: "Skype",
            description: "Skype keeps the world talking. Say “hello” with an instant message, voice or video call, no matter what device they use Skype on. Chat with the new Bing in Skype, and get AI-powered answers, recommendations, and inspiration. Skype is available on phones, tablets, PCs, and Macs.\n\n• Video calls – Don’t just hear the cheers, see them! Get together with 1 or 49 of your friends and family on a HD video call. Watch everyone’s smiles and cries when you tell them “We’re engaged!!!\" \n• Chat – Reach people instantly. Send messages to your friends, grab someone’s attention with @ mentions, or create a group chat to plan a family reunion. \n• Bing in Skype – Chat with the new Bing in your individual and group chats to get AI-powered answers, right within the context of your conversations. Ask for recommendations to help make plans together and get inspired and have fun by asking Bing for ideas.\n• Share – Send photos and videos with your family and friends. Save shared photos and videos to your camera roll. Share your screen to make travel plans with friends or present designs to your team.\n• Voice calls – Camera shy? Make voice calls to anyone in the world on Skype. You can also call mobile and landlines at low rates.\n• Express yourself – Liven up conversations with emoticons, GIFs, stickers, images from your device or web.\n• SMS - Use Skype to send SMS messages to mobiles worldwide.\n\nSkype to Skype calls are free. Operator data charges may apply. We recommend using an unlimited data plan or WiFi connection.\n\nMicrosoft Account required. Bing in Skype is available via the Bing preview waitlist. To learn more and sign-up visit: https://www.bing.com/new\n\n\n• Privacy and Cookies policy: https://go.microsoft.com/fwlink/?LinkID=507539\n• Microsoft Services Agreement: https://go.microsoft.com/fwlink/?LinkID=530144\n• EU Contract Summary: https://go.skype.com/eu.contract.summary\n\nAccess Permissions:\nAll permissions are optional and require consent (you can continue using Skype without granting these permissions, but certain features may not be available).\n\n• Contacts - Skype can sync and upload your device contacts to Microsoft\'s servers so that you can easily find and connect with your contacts that already use Skype.\n• Microphone - The microphone is needed for people to hear you during audio or video calls or for you to record audio messages.\n• Camera - The camera is needed for people to see you during video calls, or for you to be able to take photos or videos whilst you’re using Skype.\n• Location - You can share your location with other users or use your location to help find relevant places near you.\n• Photo Library - Storage is needed to be able to store photos or to share your photos with others you may chat with.\n• Notifications - Notifications allow users to know when messages or calls are received even when Skype is not actively being used.\n• Siri - This allows Siri to make calls in Skype.\n• Calendar - Access to the calendar is so that calls can be scheduled and added to the calendar.\n• Motion Usage - Motion usage is used so that Skype can detect when your device is rotated, so that Skype can orientate the camera correctly.",
            artworkUrl60: URL(string: "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/eb/9f/85/eb9f8503-3e19-f0f5-f513-985e05268298/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg")!)
        let model2 = ListItemModel(
            trackId: 333903271,
            trackName: "Twitter",
            description: "Join the conversation!\n\nRetweet, chime in on a thread, go viral, or just scroll through the Twitter timeline to stay on top of what everyone’s talking about. Twitter is your go-to social media app and the new media source for what\'s happening in the world, straight from the accounts of the influential people who affect your world day-to-day.\n\nExplore what’s trending in the media, or get to know thought-leaders in the topics that matter to you; whether your interests range from #Kpop Twitter to politics, news or sports, you can follow & speak directly to influencers or your friends alike. Every voice can impact the world. \n\nFollow your interests. Tweet, Retweet, Reply to Tweets, Share or Like - Twitter is the #1 social media app for latest news & updates.\n\nTap into what’s going on around you. Search hashtags and trending topics to stay updated on your friends & other Twitter followers. Follow the tweets of your favorite influencers, alongside hundreds of interesting Twitter users, and read their content at a glance.  \n\nShare your opinion. Engage your social network with noteworthy links, photos and videos. DM your friends or reply in a thread. Whether you chat privately or go viral, your voice makes a difference.\n\nGet noticed. Twitter allows you to find interesting people or build a following of people who are interested in you. Maintaining a social connection has never been easier! Beyond chatting with friends, Twitter allows influencers to build a personal connection with their fans. Speak directly to the people who influence you - you may be surprised by how many answer back. \n\nBuild your profile:\n*Customize your profile, add a photo, description, location, and background photo \n*Tweet often and optimize your posting times \n*Post visual content \n*Use hashtags in your Tweets \n*Draw in followers outside of Twitter \n\nTrack What’s Trending\n\nDiscover top trending hashtags and breaking news headlines. Follow media topics, Tweet threads & live videos, to keep your finger on the pulse of what’s happening. Whether you’re interested in sports highlights, pop culture memes or politics, Twitter is your source of information.\n\nJoin a community\n\nFind like-minded friends or explore interests you never knew you had. Get informed on the topics that matter to you, whether your interests are mainstream or niche. You can share content or be a fly on the wall; either way, you’ll discover something new each time you open the app.\n\nFind your voice on social media - download Twitter today!\n\nLike us on Facebook: https://www.facebook.com/TwitterInc/\nFollow us on Instagram: https://www.instagram.com/twitter/?hl=en\n\nPrivacy Policy: https://twitter.com/en/privacy\nTerms and Conditions: https://twitter.com/en/tos\n\nWe share device identifying data with some advertising partners which may include app opens that happen prior to signing up. Please see here for more details: https://help.twitter.com/en/safety-and-security/data-through-partnerships",
            artworkUrl60: URL(string: "https://is3-ssl.mzstatic.com/image/thumb/Purple126/v4/0b/74/8a/0b748aac-4c10-b394-e19d-d1f2ac951427/ProductionAppIcon-1x_U007emarketing-0-7-0-0-0-85-220.png/60x60bb.jpg")!)
        
        let list = [model1, model2, model2, model1]
        let viewModel = AppListViewModel()
        viewModel.isFirstLoading = false
        viewModel.list = list
        return AppPageView().environmentObject(viewModel)
    }
}

