//
//  ErrorView.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import SwiftUI

enum ErrorType: Equatable {
    case decoding
    case noInternet
    case backend(Int)
    case unknown
}

struct ErrorView: View {
    
    let error: ErrorType
    
    var body: some View {
        VStack {
            Text("Something went wrong")
                .font(.title)
                .padding()
            Group {
                switch error {
                case .decoding:
                    Text("Please contact developer")
                case .noInternet:
                    Text("Please check your internet connection")
                case .backend(let code):
                    switch code {
                    case 503:
                        Text("Service unavailable")
                    default:
                        Text("Server error code: \(code)")
                    }
                case .unknown:
                    Text("Unknown mistake")
                }
            }
            .padding()
        }
    }
}
