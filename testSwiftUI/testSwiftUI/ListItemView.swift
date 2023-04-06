//
//  ListItemView.swift
//  testSwiftUI
//
//  Created by pchen on 2023/4/6.
//

import SwiftUI

struct ListItemView: View {
    
    @Binding var appModel: AppModel
    
    var body: some View {
        
        HStack(alignment: .center, content: {
            AsyncImage(url: URL(string: appModel.artworkUrl60)) { phase in
                if let image = phase.image {
                    image.resizable().cornerRadius(8).aspectRatio(contentMode: .fit)
                } else if phase.error != nil {
                    Color.gray
                } else {
                    ProgressView()
                }
            }
            .frame(width: 50, height: 50)
        
            VStack(alignment: .leading, content: {
                Text(appModel.trackName).font(.headline).padding(.bottom,1.0).lineLimit(1)
                
                Text(appModel.description).font(.caption2).lineLimit(2)
            })
            
            Spacer()
            
            HearView(isSelected: $appModel.isCollectioned) { selected in
            }
        })
        .padding(10)
        .background(Color.white)
        .cornerRadius(8)
        .listRowSeparator(.hidden)
        .listRowBackground(Color(.secondarySystemBackground))
        .listRowInsets(EdgeInsets.init(top: 15, leading: 20, bottom: 0, trailing: 20))
        
    }
}

