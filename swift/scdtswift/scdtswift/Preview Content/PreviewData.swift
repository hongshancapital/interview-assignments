//
//  PreviewData.swift
//  scdtswift
//
//  Created by esafenet on 2023/3/6.
//

import Foundation
import SwiftUI

let appdetailPreviewData = RowModel(artworkUrl60:
                            "https://is3-ssl.mzstatic.com/image/thumb/Purple126/v4/cf/7a/1b/cf7a1be6-c61f-967b-46bc-9fab420c25d8/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-0-85-220-0.png/60x60bb.jpg",
                          description:
                          """
                          Google Chat is an intelligent and secure communication and collaboration tool, built for teams. From ad-hoc messaging to topic-based workstream collaboration, Chat makes it easy to get work done where the conversation is happening.
                          
                          • Group collaboration that allows Google Workspace content creation and sharing (Docs, Sheets, Slides), without having to worry about granting permissions
                          • Side by side editors, one click meetings, scheduling, document creation, and shared files, tasks, and events make it easy to get work done
                          • Google search functionality, with options to filter for conversations and content that you’ve shared
                          • Ready for Enterprise, with the full benefits of Google Workspace security and access controls including Data Loss Prevention, Compliance, Admin Settings, Vault Retention, Holds, Search, and Export
                          """,
                          trackName:
                            "Google Chat")
var applistPreviewData = [RowModel](repeating: appdetailPreviewData, count: 10)
