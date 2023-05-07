//
//  NetworkUnitTest.swift
//  SequoiaDemoTests
//
//  Created by 王浩沣 on 2023/5/7.
//

import XCTest
@testable import SequoiaDemo
import Combine


/*
 AppCommand单元测试
 */
final class AppCommandUnitTest: XCTestCase {
    var disposeBag = Set<AnyCancellable>()
    
    var store = MockStore()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        store = MockStore()
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    ///测试AppLoadMoreData返回错误的情况
    func testAppLoadMoreData_responseFailure() throws {
        ///准备数据
        let mockResponse = URLResponse()
        let publisherProvider = MockErrorTaskPublisher(error: URLError(.badServerResponse))
        let expecation = expectation(description: "testAppLoadMoreData")
        store.expecation = expecation
        ///调用
        let command = AppLoadMoreData(curDataCount: 0, publisherProvider: publisherProvider)
        command.execute(in: store)
        waitForExpectations(timeout: 1) { err in
            ///检查是否符合预期
            guard let action = self.store.dispatchAction else {
                XCTAssertTrue(false)
                return
            }
            if case .loadNextPageDone(result: .failure(_)) = action {
                XCTAssertTrue(true)
            } else {
                XCTAssertTrue(false)
            }
        }
    }

    
    ///测试AppLoadMoreData返回正确的情况
    func testAppLoadMoreData_responseSuccess() throws {
        ///准备数据
        let mockResponse = URLResponse()
        let publisherProvider = MockDataTaskPublisher(mockData: self.mockRedditAppInfoData(), mockResponse: mockResponse)
        let expecation = expectation(description: "testAppLoadMoreData")
        store.expecation = expecation
        ///调用
        let command = AppLoadMoreData(curDataCount: 0, publisherProvider: publisherProvider)
        command.execute(in: store)
        waitForExpectations(timeout: 1) { err in
            ///检查是否符合预期
            guard let action = self.store.dispatchAction else {
                XCTAssertTrue(false)
                return
            }
            if case .loadNextPageDone(result: .success(_)) = action {
                XCTAssertTrue(true)
            } else {
                XCTAssertTrue(false)
            }
        }
    }
    
    
    


    func mockRedditAppInfoData() -> Data {
        let jsonStr = "{\"resultCount\":1,  \"results\": [ {\"features\":[\"iosUniversal\"],  \"advisories\":[\"Infrequent/Mild Horror/Fear Themes\", \"Frequent/Intense Profanity or Crude Humor\", \"Frequent/Intense Mature/Suggestive Themes\", \"Infrequent/Mild Realistic Violence\", \"Infrequent/Mild Cartoon or Fantasy Violence\", \"Infrequent/Mild Sexual Content and Nudity\", \"Infrequent/Mild Medical/Treatment Information\", \"Infrequent/Mild Alcohol, Tobacco, or Drug Use or References\"],  \"supportedDevices\":[\"iPhone5s-iPhone5s\", \"iPadAir-iPadAir\", \"iPadAirCellular-iPadAirCellular\", \"iPadMiniRetina-iPadMiniRetina\", \"iPadMiniRetinaCellular-iPadMiniRetinaCellular\", \"iPhone6-iPhone6\", \"iPhone6Plus-iPhone6Plus\", \"iPadAir2-iPadAir2\", \"iPadAir2Cellular-iPadAir2Cellular\", \"iPadMini3-iPadMini3\", \"iPadMini3Cellular-iPadMini3Cellular\", \"iPodTouchSixthGen-iPodTouchSixthGen\", \"iPhone6s-iPhone6s\", \"iPhone6sPlus-iPhone6sPlus\", \"iPadMini4-iPadMini4\", \"iPadMini4Cellular-iPadMini4Cellular\", \"iPadPro-iPadPro\", \"iPadProCellular-iPadProCellular\", \"iPadPro97-iPadPro97\", \"iPadPro97Cellular-iPadPro97Cellular\", \"iPhoneSE-iPhoneSE\", \"iPhone7-iPhone7\", \"iPhone7Plus-iPhone7Plus\", \"iPad611-iPad611\", \"iPad612-iPad612\", \"iPad71-iPad71\", \"iPad72-iPad72\", \"iPad73-iPad73\", \"iPad74-iPad74\", \"iPhone8-iPhone8\", \"iPhone8Plus-iPhone8Plus\", \"iPhoneX-iPhoneX\", \"iPad75-iPad75\", \"iPad76-iPad76\", \"iPhoneXS-iPhoneXS\", \"iPhoneXSMax-iPhoneXSMax\", \"iPhoneXR-iPhoneXR\", \"iPad812-iPad812\", \"iPad834-iPad834\", \"iPad856-iPad856\", \"iPad878-iPad878\", \"iPadMini5-iPadMini5\", \"iPadMini5Cellular-iPadMini5Cellular\", \"iPadAir3-iPadAir3\", \"iPadAir3Cellular-iPadAir3Cellular\", \"iPodTouchSeventhGen-iPodTouchSeventhGen\", \"iPhone11-iPhone11\", \"iPhone11Pro-iPhone11Pro\", \"iPadSeventhGen-iPadSeventhGen\", \"iPadSeventhGenCellular-iPadSeventhGenCellular\", \"iPhone11ProMax-iPhone11ProMax\", \"iPhoneSESecondGen-iPhoneSESecondGen\", \"iPadProSecondGen-iPadProSecondGen\", \"iPadProSecondGenCellular-iPadProSecondGenCellular\", \"iPadProFourthGen-iPadProFourthGen\", \"iPadProFourthGenCellular-iPadProFourthGenCellular\", \"iPhone12Mini-iPhone12Mini\", \"iPhone12-iPhone12\", \"iPhone12Pro-iPhone12Pro\", \"iPhone12ProMax-iPhone12ProMax\", \"iPadAir4-iPadAir4\", \"iPadAir4Cellular-iPadAir4Cellular\", \"iPadEighthGen-iPadEighthGen\", \"iPadEighthGenCellular-iPadEighthGenCellular\", \"iPadProThirdGen-iPadProThirdGen\", \"iPadProThirdGenCellular-iPadProThirdGenCellular\", \"iPadProFifthGen-iPadProFifthGen\", \"iPadProFifthGenCellular-iPadProFifthGenCellular\", \"iPhone13Pro-iPhone13Pro\", \"iPhone13ProMax-iPhone13ProMax\", \"iPhone13Mini-iPhone13Mini\", \"iPhone13-iPhone13\", \"iPadMiniSixthGen-iPadMiniSixthGen\", \"iPadMiniSixthGenCellular-iPadMiniSixthGenCellular\", \"iPadNinthGen-iPadNinthGen\", \"iPadNinthGenCellular-iPadNinthGenCellular\", \"iPhoneSEThirdGen-iPhoneSEThirdGen\", \"iPadAirFifthGen-iPadAirFifthGen\", \"iPadAirFifthGenCellular-iPadAirFifthGenCellular\", \"iPhone14-iPhone14\", \"iPhone14Plus-iPhone14Plus\", \"iPhone14Pro-iPhone14Pro\", \"iPhone14ProMax-iPhone14ProMax\", \"iPadTenthGen-iPadTenthGen\", \"iPadTenthGenCellular-iPadTenthGenCellular\", \"iPadPro11FourthGen-iPadPro11FourthGen\", \"iPadPro11FourthGenCellular-iPadPro11FourthGenCellular\", \"iPadProSixthGen-iPadProSixthGen\", \"iPadProSixthGenCellular-iPadProSixthGenCellular\"],  \"screenshotUrls\":[ \"https://is3-ssl.mzstatic.com/image/thumb/Purple122/v4/65/67/2c/65672cb2-880f-cac4-8cff-7bf0e957f4ce/81f9fd16-f9f8-4e89-9d0a-dc37b5547de6_app2021_iphone11_1242x2208_EN_reddit_screen1.png/392x696bb.png\",  \"https://is4-ssl.mzstatic.com/image/thumb/Purple122/v4/d5/f3/f6/d5f3f698-e0b0-b0eb-dea4-1ffacbb2724c/0ef732b0-5454-4cbf-bf4c-37668590fc3c_app2021_iphone11_1242x2208_EN_reddit_screen2.png/392x696bb.png\",  \"https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/04/b7/a6/04b7a6e7-234f-c454-7644-189807df9c9b/e94dc906-4ebd-474b-abca-e80b63e64803_app2021_iphone11_1242x2208_EN_reddit_screen3.png/392x696bb.png\",  \"https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/05/30/16/05301668-c240-76bf-32d8-b726d0a32b2e/a5ed60b6-4406-44a4-b4be-5d86467f1bf6_app2021_iphone11_1242x2208_EN_reddit_screen4.png/392x696bb.png\",  \"https://is4-ssl.mzstatic.com/image/thumb/Purple122/v4/79/0a/1a/790a1ae8-2a25-b185-adcb-04a60c22f198/a3bb1456-9343-49bf-952c-683dfdc9f9d4_app2021_iphone11_1242x2208_EN_reddit_screen5.png/392x696bb.png\"],  \"ipadScreenshotUrls\":[ \"https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/e6/39/c9/e639c90c-fc32-080e-2b1a-df8c57731a9d/23b4537c-62c8-4a1c-b3c8-c53fec2e38d9_app2021_ipad_2048x2732_EN_screen1.png/576x768bb.png\",  \"https://is5-ssl.mzstatic.com/image/thumb/Purple112/v4/ea/45/59/ea4559b2-e1ea-e4d0-6d32-6fe1be0e0fc0/b660ffbe-162a-47ea-bc18-a05e851af8b4_app2021_ipad_2048x2732_EN_screen2.png/576x768bb.png\",  \"https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/b4/cf/a6/b4cfa67d-768f-1cf5-f6fc-b4dfe8370ec8/10536079-a603-43b4-8702-c20e1c018a8b_app2021_ipad_2048x2732_EN_screen3.png/576x768bb.png\",  \"https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/40/6d/3c/406d3c68-94d9-c8e9-8fab-eefdf00db374/4a2db254-c052-4227-9542-3bcf6f206c63_app2021_ipad_2048x2732_EN_screen4.png/576x768bb.png\",  \"https://is1-ssl.mzstatic.com/image/thumb/Purple112/v4/e4/fe/d2/e4fed24e-c37f-b0c0-5a8c-fd901e541a7a/54c74e1d-274d-4902-896a-7535ecd785f3_app2021_ipad_2048x2732_EN_screen5.png/576x768bb.png\"], \"appletvScreenshotUrls\":[],  \"artworkUrl60\":\"https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/17/c5/d5/17c5d502-d561-a8ec-ce36-a7d2d04d7a1f/AppIcon-1x_U007emarketing-0-7-0-85-220.png/60x60bb.jpg\",  \"artworkUrl512\":\"https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/17/c5/d5/17c5d502-d561-a8ec-ce36-a7d2d04d7a1f/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg\",  \"artworkUrl100\":\"https://is1-ssl.mzstatic.com/image/thumb/Purple116/v4/17/c5/d5/17c5d502-d561-a8ec-ce36-a7d2d04d7a1f/AppIcon-1x_U007emarketing-0-7-0-85-220.png/100x100bb.jpg\", \"artistViewUrl\":\"https://apps.apple.com/us/developer/reddit/id808295587?uo=4\", \"isGameCenterEnabled\":false, \"kind\":\"software\", \"releaseNotes\":\"Just a pinch of an update for seasoning this week.\",  \"description\":\"Reddit is the place where people come together to have the most authentic and interesting conversations on the internet—Where gaming communities, nostalgic internet forums, bloggers, meme-makers, and fandoms mingle alongside video streamers, support groups, news junkies, armchair experts, seasoned professionals, and artists and creators of all types. \\n\\nWith over 100,000 communities about every topic you could think of (and a few you’d probably never think of if it wasn’t for the creativity of strangers on the internet), Reddit is the place where you can dive into anything and connect with people on any topic. \\n\\nA few things you’ll find on Reddit… \\n\\n■ Thousands of communities\\nWhether you're into breaking news, sports, TV fan theories, or a never-ending stream of the internet's cutest animals, there's a community on Reddit for you.\\n■ Laughs, lols, and plenty of ridiculousness \\nLose track of vast amounts of time as you find memes, bananas for scale, bread stapled to trees, cat videos, and more of the absurd and oddly absorbing. \\n■ The best discussions \\nThe real action is always in the comments. Reddit’s discussion threads are where community members jump in to provide commentary, humor, and insight. \\n■ Answers to questions you’re too afraid to ask in public \\nRecipes, street fashion, career help, fitness plans, and more—find ideas and inspiration for whatever you want to do.\\n■ Live video streams, chats, and talks\\nWant to know what people are doing right now? Streaming videos, live chats, and live audio conversations give you a variety of ways to connect with people in the moment. \\n■ Crowd-sourced points of view on just about everything\\nProduct reviewers, music critics, sports fans, or doge enthusiasts—find people that obsess and care about whatever it is you’re interested in.\\n■ Anonymous profiles so you can do you\\nOn Reddit, you (not your job, number of friends, or social status) define who you are. \\n■ Lots and lots of cats \\n\\nReddit Premium:\\nPurchase Reddit Premium to enjoy an ads-free experience and access to premium avatar gear, r/lounge, custom app icons, and more.\\n\\nPayment will be charged on a recurring monthly or annual basis to your Apple ID account. Your monthly or annual Premium subscription will automatically renew unless you cancel at least 24 hours before your subscription ends. Cancel anytime in your device’s Account Settings. No partial refunds. \\n\\nPrivacy Policy: https://www.redditinc.com/policies/privacy-policy\\nUser Agreement: https://www.redditinc.com/policies/user-agreement\\nContent Policy: https://www.redditinc.com/policies/content-policy\\n\\nDownload the official Reddit app today.\", \"sellerName\":\"REDDIT, INC.\", \"bundleId\":\"com.reddit.Reddit\", \"genreIds\":[\"6009\", \"6005\"], \"currentVersionReleaseDate\":\"2023-04-24T18:47:42Z\", \"trackId\":1064216828, \"trackName\":\"Reddit\", \"isVppDeviceBasedLicensingEnabled\":true, \"releaseDate\":\"2016-04-07T08:12:27Z\", \"trackCensoredName\":\"Reddit\", \"languageCodesISO2A\":[\"EN\", \"FR\", \"DE\", \"IT\", \"PT\", \"ES\"], \"fileSizeBytes\":\"236012544\", \"sellerUrl\":\"https://www.reddit.com/mobile/download\", \"formattedPrice\":\"Free\", \"contentAdvisoryRating\":\"17+\", \"averageUserRatingForCurrentVersion\":4.81838999999999995083044268540106713771820068359375, \"userRatingCountForCurrentVersion\":2611003, \"averageUserRating\":4.81838999999999995083044268540106713771820068359375, \"trackViewUrl\":\"https://apps.apple.com/us/app/reddit/id1064216828?uo=4\", \"trackContentRating\":\"17+\", \"currency\":\"USD\", \"minimumOsVersion\":\"14.0\", \"primaryGenreName\":\"News\", \"primaryGenreId\":6009, \"price\":0.00, \"artistId\":808295587, \"artistName\":\"reddit\", \"genres\":[\"News\", \"Social Networking\"], \"version\":\"2023.16.0\", \"wrapperType\":\"software\", \"userRatingCount\":2611003}] } "
        return jsonStr.data(using: .utf8) ?? Data()
    }
}
