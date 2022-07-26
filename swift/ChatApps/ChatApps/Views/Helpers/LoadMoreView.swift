//
//  LoadMoreView.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/24.
//

import SwiftUI

struct LoadMoreView: View {
  var hasMore: Bool
  
  var body: some View {
    HStack {
      Spacer()
      if hasMore {
        ProgressView()
      } else {
        Text("No more data.")
          .foregroundColor(.gray)
      }
      Spacer()
    }
    
  }
}

struct LoadMoreView_Previews: PreviewProvider {
  static var previews: some View {
    LoadMoreView(hasMore: false)
      .previewLayout(.fixed(width: 375, height: 80))
  }
}
