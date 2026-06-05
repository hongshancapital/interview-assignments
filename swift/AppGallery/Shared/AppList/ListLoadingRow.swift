//
//  ListLoadingRow.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import SwiftUI

struct ListLoadingRow: View {
    
    var hasMoreData: Bool
    
    var loading: Bool
    
    var body: some View {
        Group {
            if loading {
                HStack(spacing: 4) {
                    ProgressView()
                    Text("Loading...")
                }
            } else if !hasMoreData {
                Text("No more data.")
            }
        }
        .font(.title3)
        .foregroundColor(.secondary)
    }
    
}

struct ListLoadingRow_Previews: PreviewProvider {
    static var previews: some View {
        ListLoadingRow(hasMoreData: true, loading: true)
    }
}
