import Foundation

protocol LoaderConfig {
    var url: URL { get }
}

enum CardPageLoaderConfig: LoaderConfig {
    case cardList
    
    var url: URL {
        Bundle.main.url(forResource: "mock.json", withExtension: nil)!
    }
}
