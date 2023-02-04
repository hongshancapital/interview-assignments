//

//
//  AsyncImagePhase.swift
//  InterviewDemo
//
//  Created by 霍橙 on 2023/2/3.
//  支持iOS14的AsyncImage
//
    

import SwiftUI

@available(iOS, deprecated: 15.0)
public enum AsyncImagePhase {
    case empty
    case success(Image)
    case failure(Error)
    
    public var image: Image? {
        switch self {
        case .empty, .failure:
            return nil
        case .success(let image):
            return image
        }
    }
    
    public var error: Error? {
        switch self {
        case .empty, .success:
            return nil
        case .failure(let error):
            return error
        }
    }
}
