//
//  LoadableState.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation

enum LoadableState<Content> {

    case idle
        
    case loading
    
    case loaded(content: Content, couldLoadMore: Bool)
    
    case error(Error)
    
}
