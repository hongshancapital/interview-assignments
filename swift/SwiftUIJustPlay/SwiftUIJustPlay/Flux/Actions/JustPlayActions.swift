//
//  JustPlayActions.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import Foundation
import SwiftUIFlux

struct JustPlayActions {
    
    struct FetchAppStoreList: AsyncAction {
        let page: PageEndPoint;
        
        func execute(state: FluxState?, dispatch: @escaping DispatchFunction) {
            APIService.shared.GET(page: page) {
                (result: Result<AppResult<AppItem>, APIService.APIError>) in
                switch result {
                case let .success(response):
                    if (self.page.pageIndex() == 0) {
                        dispatch(RefreshAppStoreList(page: self.page,
                                            response: response))
                    } else {
                        dispatch(SetAppStoreList(page: self.page,
                                            response: response))
                    }
                case let .failure(error):
                    print(error)
                    break
                }
            }
        }
    }
        
    struct SetAppStoreList: Action {
        let page: PageEndPoint
        let response: AppResult<AppItem>
    }
    
    struct RefreshAppStoreList: Action {
        let page: PageEndPoint
        let response: AppResult<AppItem>
    }

}
