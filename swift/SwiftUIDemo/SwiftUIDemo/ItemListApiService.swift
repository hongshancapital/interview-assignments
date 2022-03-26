//
//  ItemListApiService.swift
//  SwiftUIDemo
//
//  Created by didi_qihang on 2022/3/13.
//

import Foundation

class ItemListApiService {
    static let shared = ItemListApiService()
    
    func getItemList() -> ItemListModel? {
//        let url = URL(string: "http://localhost:3000/post")
//        guard let url = url else {
//            return Fail(error: NetworkError.URLError).eraseToAnyPublisher()
//        }
//
//        return URLSession.shared.dataTaskPublisher(for: url)
//            .tryMap { element -> Data in
//                guard let httpResponse = element.response as? HTTPURLResponse,
//                      httpResponse.statusCode == 200 else {
//                          throw URLError(.badServerResponse)
//                      }
//                return element.data
//
//            }
//            .decode(type: ItemListModel.self, decoder: JSONDecoder())
//            .eraseToAnyPublisher()
        
        let json = """
        {
            "code": 200,
            "msg": "success",
            "items": [
                {
                    "head_url": "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/91/6f/8a/916f8a02-6467-51a7-516e-bad1a86203bc/source/60x60bb.jpg",
                    "title": "Science Mobile, LLC",
                    "sub_title": "MeetMe helps you find new people nearby who share your interests and want to chat now! It fun, friendly, and free!oin 100+ MILLION PEOPLE chatting and making new friends.o what are you waiting for? Download the",
                    "selected": true
                },
                {
                    "head_url": "https://is5-ssl.mzstatic.com/image/thumb/Purple116/v4/94/13/86/941386e1-c5ab-1557-43fa-fbabed2ea8eb/source/60x60bb.jpg",
                    "title": "FunPokes Inc.",
                    "sub_title": "Download Tango now and become part of a social video live stream platform where you can discover talented video creators and make new friends fro",
                    "selected": true
                },
                {
                    "head_url": "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/a5/d5/ad/a5d5ada4-ecb3-2434-ed1d-3e20e60944c9/source/60x60bb.jpg",
                    "title": "AMZN Mobile LLC",
                    "sub_title": "Skype keeps the world talking. Say  with an instant message, voice or video call all for free*, no matter what device they use Skype on.",
                    "selected": false
                },
                {
                    "head_url": "https://is5-ssl.mzstatic.com/image/thumb/Purple116/v4/dd/07/2f/dd072fe4-9213-d3be-a256-9a23bfc7c5ed/source/60x60bb.jpg",
                    "title": "Skype Communications S.a.r.l",
                    "sub_title": "Be together whenever, with our free* all-in-one communication app, complete with unlimited text, voice, video calling and group video chat.",
                    "selected": true
                },
                {
                    "head_url": "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/8d/ed/8f/8ded8ff8-8d95-f5c0-39ed-2b4dd202f8e7/source/60x60bb.jpg",
                    "title": "Google LLC",
                    "sub_title": "Connect with friends, family and people who share the same interests as you. Communicate privately, watch your favorite content, buy and sell items or .",
                    "selected": false
                },
                {
                    "head_url": "https://is3-ssl.mzstatic.com/image/thumb/Purple126/v4/0e/34/6c/0e346cf6-b384-50dd-7d96-556758b929c8/source/60x60bb.jpg",
                    "title": "Match Group, LLC",
                    "sub_title": "Millions of people have signed up for Bumble to start building valuable relationships, finding friends, and making empowered connections. And now, weve",
                    "selected": false
                },
                {
                    "head_url": "https://is3-ssl.mzstatic.com/image/thumb/Purple116/v4/f8/da/ba/f8daba71-865c-da97-90ce-be1eaaf3efef/source/60x60bb.jpg",
                    "title": "UNEARBY LIMITED",
                    "sub_title": "LoopChat is the #1 plug for all things college: find your class chats, host virtual parties, and meet students across the nation!",
                    "selected": false
                },
                {
                    "head_url": "https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/c3/41/a3/c341a309-97c2-e02b-0ec0-f992f0b820c6/source/60x60bb.jpg",
                    "title": "Viber Media SARL.",
                    "sub_title": "Marco Polo combines the best of texting, social media and video chats - all in one private, easy to use app.S IT SOCIAL MEDIAts different from",
                    "selected": false
                },
                {
                    "head_url": "https://is3-ssl.mzstatic.com/image/thumb/Purple126/v4/f5/3b/5e/f53b5efc-3c56-b7f3-7f85-b29d884c1636/source/60x60bb.jpg",
                    "title": "Kik Interactive Inc.",
                    "sub_title": "Snapchat is a fast and fun way to share the moment with your friends and familnapchat opens right to the Camera just tap to take a photo",
                    "selected": false
                },
                {
                    "head_url": "https://is5-ssl.mzstatic.com/image/thumb/Purple126/v4/e3/8c/52/e38c52c1-9bec-1261-200e-39a5d762b89b/source/60x60bb.jpg",
                    "title": "TangoMe, Inc.",
                    "sub_title": "Welcome to Tinder you can be here for a good time, and a long time, too. With 30 billion matches to date, Tinderis the top free dating app, making.",
                    "selected": false
                }
            ]
        }
        """.data(using: .utf8)!
        
        let decoder = JSONDecoder()
        do {
            let product = try decoder.decode(ItemListModel.self, from: json)
            return product

        } catch let error {
            print(error)
            return nil
        }
    }
}
