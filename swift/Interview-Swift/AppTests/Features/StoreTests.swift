//
//  StoreTests.swift
//  AppTests
//
//  Created by lizhao on 2022/9/28.
//

import Quick
import Nimble
import GCore
import Combine
import Moya

@testable import App

final class StoreTests : QuickSpec {
    override func spec() {
        
        describe("AppStore") {
            var appState: AppState!
            var store: Store!
            
            beforeEach {
                appState = AppState()
                store = Store()
            }
            
            it("refresh cmd") {
                let result = Store.reduce(state: appState, action: .refresh)
                let newState = result.0
                let cmd: LoadAppListCommand? = result.1 as? LoadAppListCommand

                expect(cmd).notTo(beNil())
                expect(newState.appList.page).to(equal(1))
                expect(newState.appList.loadmoreState).to(equal(.hidden))
                expect(newState.appList.canLoadMore).to(equal(true))
                expect(newState.appList.loadingListError).to(beNil())
            }
            
            it(".loadApplist cmd") {
                let firstPageAppState = MockData.List.mockLoadedFirstPageAppState
                let result = Store.reduce(state: firstPageAppState, action: .loadApplist)
                let newState = result.0
                let cmd: LoadAppListCommand? = result.1 as? LoadAppListCommand
                
                expect(cmd).notTo(beNil())
                expect(newState.appList.page).to(equal(2))
                expect(newState.appList.loadmoreState).to(equal(.hidden))
                expect(newState.appList.canLoadMore).to(equal(true))
                expect(newState.appList.loadingListError).to(beNil())
            }
            
            
            it(".loadApplistDone: load 10 models") {
                let firstPageAppState = MockData.List.mockLoadedFirstPageAppState
                
                let (newState, cmd) = Store.reduce(state: firstPageAppState, action: .loadApplistDone(result: .success(MockData.List.mock10AppModel)))
 
                expect(cmd).to(beNil())
                expect(newState.appList.loadingListError).to(beNil())
                expect(newState.appList.apps.count).to(equal(20))
                expect(newState.appList.canLoadMore).to(beTrue())
                expect(newState.appList.page).to(equal(3))
            }
            
            it(".loadApplistDone: empty") {
                let firstPageAppState = MockData.List.mockLoadedFirstPageAppState
                
                let (newState, cmd) = Store.reduce(state: firstPageAppState, action: .loadApplistDone(result: .success([])))
 
                expect(cmd).to(beNil())
                expect(newState.appList.loadingListError).to(beNil())
                expect(newState.appList.apps.count).to(equal(10))
                expect(newState.appList.canLoadMore).to(beFalse())
                expect(newState.appList.loadmoreState).to(equal(.noMoreData))
                expect(newState.appList.page).to(equal(2))
            }
            
            
            it(".loadApplistDone: failure") {
                let firstPageAppState = MockData.List.mockLoadedFirstPageAppState
                let mockError = MockData.MockError.networkingFailed
                let (newState, cmd) = Store.reduce(state: firstPageAppState, action: .loadApplistDone(result: .failure(mockError)))
 
                let loadingListError = newState.appList.loadingListError
                expect(loadingListError).notTo(beNil())
                expect(cmd).to(beNil())
                expect(newState.appList.apps.count).to(equal(10))
                expect(newState.appList.canLoadMore).to(beTrue())
                expect(newState.appList.page).to(equal(2))
            }
            
            it(".toggleFavorite") {
                let firstPageAppState = MockData.List.mockLoadedFirstPageAppState
                let result = Store.reduce(state: firstPageAppState, action: .toggleFavorite(trackId: 1, isFavorite: true))
                let cmd: FavoriteAppCommand? = result.1 as? FavoriteAppCommand
                let newState = result.0
   
                expect(cmd).notTo(beNil())
                expect(newState.appList.apps.count).to(equal(10))
                expect(newState.appList.canLoadMore).to(beTrue())
                expect(newState.appList.page).to(equal(2))
            }
            
            
            it(".toggleFavoriteDone: success") {
                let firstPageAppState = MockData.List.mockLoadedFirstPageAppState
 
                let result = Store.reduce(state: firstPageAppState, action: .toggleFavoriteDone(result: MockData.FavoriteDone.success))
                
                let cmd: FavoriteAppCommand? = result.1 as? FavoriteAppCommand
                let newState = result.0
                let isFavorite = newState.favoriteState(id: 1)
                expect(cmd).to(beNil())
                expect(isFavorite).notTo(beNil())
                expect(isFavorite).to(beTrue())
                expect(newState.appList.apps.count).to(equal(10))
                expect(newState.appList.canLoadMore).to(beTrue())
                expect(newState.appList.page).to(equal(2))
            }
            it(".toggleFavoriteDone: failed") {
                let firstPageAppState = MockData.List.mockLoadedFirstPageAppState
                let result = Store.reduce(state: firstPageAppState, action: .toggleFavoriteDone(result: MockData.FavoriteDone.failed))
                
                let cmd: FavoriteAppCommand? = result.1 as? FavoriteAppCommand
                let newState = result.0
                expect(cmd).to(beNil())
                expect(newState.appList.apps.count).to(equal(10))
                expect(newState.appList.canLoadMore).to(beTrue())
                expect(newState.appList.page).to(equal(2))
            }
        }
    }
    
    
    
    private func readFavoriteState(state: AppState, id: Int) -> Bool? {
        let model = state.appList.apps.first {
            $0.app.trackId == id
        }
        
        return model?.isFavorite
    }
}



