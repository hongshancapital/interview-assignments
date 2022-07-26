//
//  AppIcon.swift
//  ChatApps
//
//  Created by XuNing on 2022/7/23.
//

import SwiftUI

private struct Overlay: ViewModifier {
  func body(content: Content) -> some View {
    content
      .cornerRadius(8)
      .overlay(RoundedRectangle(cornerRadius: 8)
        .stroke(.gray, lineWidth: onePixel))
  }
}

private extension View {
  func roundedOverlay() -> some View {
    modifier(Overlay())
  }
}

struct AppIcon: View {
  var url: String
  
  var body: some View {
    // Should use `KFImage` in actual work.
    AsyncImage(url: URL(string: url)) { phase in
      if let image = phase.image {
        image.resizable()
          .roundedOverlay()
      } else if phase.error != nil {
        Color(uiColor: UIColor.systemGray6)
          .roundedOverlay()
      } else {
        ProgressView()
      }
    }
  }
}

struct AppIcon_Previews: PreviewProvider {
  static var previews: some View {
    AppIcon(url: MockData.page1.results[0].artworkUrl100)
  }
}
