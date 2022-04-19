//
//  Row.swift
//  homework
//
//  Created by 干饭人肝不完DDL on 2022/4/11.
//

import SwiftUI

struct Row: View {
    var app: AppModel
    @State var isFavorite: Bool = false
    var body: some View {
        HStack(alignment: .center, spacing: 20) {
            ImageView(app: app)
                .frame(width:60, height: 60)
                .cornerRadius(8)
            VStack (alignment: .leading, spacing: 8){
                Text(app.trackCensoredName)
                    .fontWeight(.semibold)
                    .lineLimit(1)
                Text(app.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            Button{
                withAnimation {
                    self.isFavorite.toggle()
                }
            } label: {
                Image(systemName: isFavorite ? "heart.fill" : "heart")
                    .foregroundColor(isFavorite ? .red : .gray)
                    .scaleEffect(isFavorite ? 1.1 : 1)   
            }
        }
        .frame(maxWidth: .infinity)
        .padding(8)
        .background(RoundedRectangle(cornerRadius: 16).foregroundColor(.white))
        .padding(.vertical,4)
        .background(Color.clear)
    }
}

struct Row_Previews: PreviewProvider {
    static var previews: some View {
        Row(app: AppModel(screenshotUrls: [],
                          ipadScreenshotUrls: [],
                          appletvScreenshotUrls: [],
                          artworkUrl60: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/60x.jpg",
                          artworkUrl512: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/512x512bb.jpg",
                          artworkUrl100: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/ed/ef/ca/edefcae0-f7fd-9531-2218-f9ab82519acb/source/100x100bb.jpg",
                          artistViewUrl: "https://apps.apple.com/us/developer/google-llc/id281956209?uo=4",
                          features: ["iosUniversal"],
                          supportedDevices: [],
                          advisories: [],
                          isGameCenterEnabled: false,
                          kind: "software",
                          minimumOsVersion: "12.0",
                          trackCensoredName: "Google Chat",
                          languageCodesISO2A: ["AF", "AR", "EU", "BN", "BS", "BG", "CA", "HR", "CS", "DA", "NL", "EN", "ET", "FI", "FR", "GL", "DE", "EL", "GU", "HE", "HI", "HU", "IS", "ID", "IT", "JA", "KN", "KO", "LV", "LT", "MS", "ML", "MR", "NB", "FA", "PL", "PT", "RO", "RU", "SR", "ZH", "SK", "SL", "ES", "SW", "SV", "TA", "TE", "TH", "ZH", "TR", "UK", "UR", "VI", "ZU"],
                          fileSizeBytes: "235096064",
                          sellerUrl: "https://workspace.google.com/products/chat/",
                          formattedPrice: "Free",
                          contentAdvisoryRating: "4+",
                          averageUserRatingForCurrentVersion: 4.6136900000000000687805368215776979923248291015625,
                          userRatingCountForCurrentVersion: 40738,
                          averageUserRating: 4.6136900000000000687805368215776979923248291015625,
                          trackViewUrl: "https://apps.apple.com/us/app/google-chat/id1163852619?uo=4",
                          trackContentRating: "4+",
                          bundleId: "com.google.Dynamite",
                          primaryGenreId: 6000,
                          trackId: 1163852619,
                          trackName: "Google Chat",
                          releaseDate: "2018-02-28T13:37:21Z",
                          primaryGenreName: "Business",
                          genreIds: ["6000", "6007"],
                          isVppDeviceBasedLicensingEnabled: true,
                          currentVersionReleaseDate: "2022-02-07T17:36:14Z",
                          sellerName: "Google LLC",
                          releaseNotes: "Google Chat, part of Google Workspace, is now available to anyone with a Gmail address, for free! Use Chat to collaborate seamlessly either one-on-one, or in topic-based group conversations. Get things done and create content easily with Google Docs, Sheets, and Slides, and keep everyone up to date with shared files and tasks. Easily find past conversations or files with Google’s powerful search, and let bots and smart suggestions help you connect with meeting participants and get more done, faster.\n\nYou will now also see that Chat rooms are now called spaces",
                          currency: "USD",
                          description: "Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.\n\n• Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions\n• Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done\n• Google search functionality, with options to filter for conversations and content that you’ve shared\n• Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export",
                          artistId: 281956209,
                          artistName: "Google LLC",
                          genres: ["Business", "Productivity"],
                          price: 0.00,
                          version: "1.0.142",
                          wrapperType: "software",
                          userRatingCount: 40738))
    }
}
