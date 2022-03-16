//
//  RefreshFooter.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/6.
//

import SwiftUI

struct RefreshFooter: View {
    var hasMoreData: Bool
    
    var body: some View {
        HStack {
            Spacer()
            
            if hasMoreData {
                ActivityIndicator(style: .medium)
                
                Text("Loading...")
                    .foregroundColor(Color.h8f8e94)
            } else {
                Text("No more data.")
                    .foregroundColor(Color.h8f8e94)
            }
            
            Spacer()
        }
    }
}

struct RefreshFooter_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            RefreshFooter(hasMoreData: true)

            RefreshFooter(hasMoreData: false)
        }
    }
}
